package io.acmus.granadaartifactsonline.wizard.converter;

import io.acmus.granadaartifactsonline.wizard.Wizard;
import io.acmus.granadaartifactsonline.wizard.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardDtoToWizardConverter implements Converter<WizardDto, Wizard> {

    @Override
    public Wizard convert(WizardDto source) {

        Wizard wizard = new Wizard();

        wizard.setId(source.id());
        wizard.setName(source.name());

        return wizard;
    }
}
