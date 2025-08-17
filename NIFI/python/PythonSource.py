from nifiapi.flowfilesource import FlowFileSource, FlowFileSourceResult
from nifiapi.properties import PropertyDescriptor, StandardValidators

class PythonCreate(FlowFileSource):
    class Java:
        implements = ['org.apache.nifi.python.processor.FlowFileSource']

    class ProcessorDetails:
        version = '0.0.1'
        description = '''Генератор файлов'''
        tags = [
            "biconsult"
        ]

    def __init__(self, **kwargs):
        generated_text = PropertyDescriptor(name="Text",
                    description="Text to generate",
                    default_value="Sample text",
                    required=True)

        count = PropertyDescriptor(name="Count",
                                        description="Count of text",
                                        validators=[StandardValidators.POSITIVE_INTEGER_VALIDATOR],
                                        default_value="1",
                                        required=True)

        self.descriptors = [generated_text,count]


    def getPropertyDescriptors(self):
        return self.descriptors

    def onScheduled(self, context) -> None:
        self.text=context.getProperty("Text").evaluateAttributeExpressions().getValue()
        self.count=int(context.getProperty("Count").evaluateAttributeExpressions().getValue())

    def onStopped(self, context) -> None:
        pass

    def create(self, context):
        result=self.text*self.count
        return FlowFileSourceResult(relationship = 'success', attributes = {"repeted":str(self.count)}, contents = result)