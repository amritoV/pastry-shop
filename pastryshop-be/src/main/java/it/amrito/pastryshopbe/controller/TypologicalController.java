package it.amrito.pastryshopbe.controller;

import it.amrito.pastryshopbe.dto.TypologicalSweetDto;
import it.amrito.pastryshopbe.dto.TypologicalSweetLiteDto;
import it.amrito.pastryshopbe.model.TypologicalSweetModel;
import it.amrito.pastryshopbe.service.TypologicalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/typological")
public class TypologicalController {

    @Autowired
    TypologicalService typologicalService;

    @GetMapping("")
    public ResponseEntity<List<TypologicalSweetLiteDto>> getAll(){
        return ResponseEntity.ok(typologicalService.findAllDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypologicalSweetDto> getById(@PathVariable("id") Long id){
        Optional<TypologicalSweetDto> sweetDtoOpt = typologicalService.findDtoById(id);
        return sweetDtoOpt.map(x -> ResponseEntity.ok().body(x))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TypologicalSweetLiteDto> save(@Valid @RequestBody TypologicalSweetDto sweetDto){
        Optional<TypologicalSweetModel> modelOpt = typologicalService.findModelByNameIgnoreCase(sweetDto.getName());
        if(modelOpt.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //todo messaggio di errore piu specifico
        }
        return ResponseEntity.ok(typologicalService.save(sweetDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypologicalSweetLiteDto> update(@PathVariable("id") Long id,
                                                              @Valid @RequestBody TypologicalSweetDto sweetDto){
        //se la tipologica dolce da aggiornare non esiste, ritorno NOT FOUND
        Optional<TypologicalSweetModel> modelOpt = typologicalService.findModelById(id);
        if(modelOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        TypologicalSweetModel sweetModel = modelOpt.get();
        //se voglio modificare il nome della tipologica dolce ma tale nome è già esistente, ritorno BAD REQUEST
        if(!sweetDto.getName().equalsIgnoreCase(sweetModel.getName())){
            Optional<TypologicalSweetModel> opt = typologicalService.findModelByNameIgnoreCase(sweetDto.getName());
            if(opt.isPresent()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//todo gestire messaggio d'errore
            }
        }
        TypologicalSweetLiteDto sweetLiteDtoUpdated = typologicalService.update(sweetDto, sweetModel);
        return ResponseEntity.ok(sweetLiteDtoUpdated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        Optional<TypologicalSweetModel> model = typologicalService.findModelById(id);
        if(!model.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //todo gestire messaggio d'errore
        typologicalService.deleteById(id);
        return ResponseEntity.ok().body("");
    }
}
