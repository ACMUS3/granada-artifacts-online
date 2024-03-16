package io.acmus.granadaartifactsonline.wizard;

import io.acmus.granadaartifactsonline.system.Result;
import io.acmus.granadaartifactsonline.system.StatusCode;
import io.acmus.granadaartifactsonline.wizard.converter.WizardDtoToWizardConverter;
import io.acmus.granadaartifactsonline.wizard.converter.WizardToWizardDtoConverter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/wizards")
public class WizardController {
    private final WizardService wizardService;
    private final WizardDtoToWizardConverter wizardDtoToWizardConverter;
    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public WizardController(WizardService wizardService,
                            WizardDtoToWizardConverter wizardDtoToWizardConverter,
                            WizardToWizardDtoConverter wizardToWizardDtoConverter) {

        this.wizardService = wizardService;
        this.wizardDtoToWizardConverter = wizardDtoToWizardConverter;
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }

    @GetMapping("/{wizardId}")
    public Result findWizardById(@PathVariable Integer wizardId) {
        Wizard wizard = this.wizardService.findById(wizardId);
        WizardDto wizardDto = this.wizardToWizardDtoConverter.convert(wizard);
        return new Result(true, StatusCode.Success, "Find One Success", wizardDto);
    }

    @GetMapping("/")
    public Result findAllWizards() {
        List<Wizard> wizardList = this.wizardService.findAll();
        List<WizardDto> wizardDtoList = wizardList.stream()
                .map(this.wizardToWizardDtoConverter::convert)
                .toList();
         return new Result(true, StatusCode.Success, "Find All Success", wizardDtoList);
    }

    @PostMapping
    public Result addWizard(@Valid @RequestBody WizardDto wizardDto) {
        Wizard newWizard = this.wizardDtoToWizardConverter.convert(wizardDto);
        Wizard savedWizard = this.wizardService.save(newWizard);
        WizardDto savedWizardDto = this.wizardToWizardDtoConverter.convert(savedWizard);
        return new Result(true, StatusCode.Success, "Add Success", savedWizardDto);


    }

    @PutMapping("/{wizardId}")
    public Result updateWizard(@PathVariable Integer wizardId
            , @Valid @RequestBody WizardDto wizardDto) {

        Wizard update = this.wizardDtoToWizardConverter.convert(wizardDto);
        Wizard updatedWizard = this.wizardService.update(wizardId, update);
        WizardDto updatedWizardDto = this.wizardToWizardDtoConverter.convert(updatedWizard);
        return new Result(true, StatusCode.Success, "Update Success", updatedWizardDto);

    }


    @DeleteMapping("/{wizardId}")
    public Result deleteWizard(@PathVariable Integer wizardId) {
        this.wizardService.delete(wizardId);
        return new Result(true, StatusCode.Success, "Delete Success");

    }


}











