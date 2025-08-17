from nifiapi.flowfiletransform import FlowFileTransform, FlowFileTransformResult
from nifiapi.properties import PropertyDescriptor, StandardValidators
from nifiapi.relationship import Relationship

TEXT_TO_REPLACE_PROPERTY = PropertyDescriptor(name="replace_value",
                                              description="Text to generate",
                                              default_value="Sample text",
                                              required=True)


class PythonTransform(FlowFileTransform):
    class Java:
        implements = ['org.apache.nifi.python.processor.FlowFileTransform']

    class ProcessorDetails:
        version = '0.0.1'
        description = '''
        Testing transform processor
        '''
        dependencies = []
        tags = ['biconsult']

    def __init__(self, **kwargs):
        success = Relationship(name="success",
                               description='',
                               auto_terminated=False)
        self.relations = [success]
        self.descriptors = [TEXT_TO_REPLACE_PROPERTY]

    def getPropertyDescriptors(self):
        return self.descriptors

    def getRelationships(self):
        return self.relations

    def onScheduled(self, context) -> None:
        self.text = context.getProperty(TEXT_TO_REPLACE_PROPERTY).evaluateAttributeExpressions().getValue()
        self.replace = "*" * len(self.text)

    def onStopped(self, context) -> None:
        pass

    def transform(self, context, flowfile):
        result = flowfile.getContentsAsBytes().decode('UTF-8').replace(self.text, self.replace)
        return FlowFileTransformResult(relationship="success", contents=result,
                                       attributes={"text": self.text, "replace": self.replace})
