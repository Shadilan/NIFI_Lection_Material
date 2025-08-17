/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.study.processor;

import org.apache.nifi.annotation.behavior.*;
import org.apache.nifi.annotation.lifecycle.OnAdded;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.expression.ExpressionLanguageScope;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.util.StandardValidators;

import java.util.List;
import java.util.Set;

@Tags({"example"})
@CapabilityDescription("Provide a description") //Описание возможностей
@SeeAlso({}) //кросс ссылки
@ReadsAttributes({@ReadsAttribute(attribute="", description="")}) //Используемые атрибуты
@WritesAttributes({@WritesAttribute(attribute="", description="")}) //Изменяемые атрибуты
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED) //Входящие очереди
public class MyProcessor extends AbstractProcessor {

    //Пример описания параметра процессора
    public static final PropertyDescriptor MY_PROPERTY = new PropertyDescriptor
            .Builder()
            .name("My Property")
            .displayName("My Property")
            .description("Example Property")
            .allowableValues("Value1","Value2")// Выбор значений
            .defaultValue("Value1")//Значение по умолчанию
            .sensitive(false) //Чувстительное значение для паролей
            .expressionLanguageSupported(ExpressionLanguageScope.FLOWFILE_ATTRIBUTES) //Поодержка языка выражений
            .required(true) //Обязательность
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR) //Проверки значения
            .build();

    //Пример описания исходящей очереди
    public static final Relationship REL_SUCCESS = new Relationship.Builder()
            .name("success")
            .description("Example success relationship")
            .build();

    private List<PropertyDescriptor> descriptors;

    private Set<Relationship> relationships;

    @Override
    protected void init(final ProcessorInitializationContext context) {
        //Инициализация процессора
        //Добавлем все параметры процессора
        descriptors = List.of(MY_PROPERTY);

        //И все исходящии очереди
        relationships = Set.of(REL_SUCCESS);
    }

    @Override
    public Set<Relationship> getRelationships() {

        return this.relationships;
    }

    @Override
    public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {

        return descriptors;
    }


    @OnAdded
    public void onAdded(){
        //Выполняется при загрузке и добавлении на Канвас
    }


    @OnScheduled
    public void onScheduled(final ProcessContext context) {
        //Выполняется при запуске
    }

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) {
        //Получение файла
        FlowFile flowFile = session.get();
        if (flowFile == null) {
            //Проверка что файл найден
            return;
        }
        // Обработка файла

        // Передача файла  исходящую очередь
        session.transfer(flowFile, REL_SUCCESS);
    }
}
