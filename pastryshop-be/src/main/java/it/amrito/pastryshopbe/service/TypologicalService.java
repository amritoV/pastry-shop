package it.amrito.pastryshopbe.service;

import it.amrito.pastryshopbe.dto.IngredientDto;
import it.amrito.pastryshopbe.dto.TypologicalSweetDto;
import it.amrito.pastryshopbe.dto.TypologicalSweetLiteDto;
import it.amrito.pastryshopbe.model.IngredientModel;
import it.amrito.pastryshopbe.model.SweetIngredientId;
import it.amrito.pastryshopbe.model.SweetIngredientModel;
import it.amrito.pastryshopbe.model.TypologicalSweetModel;
import it.amrito.pastryshopbe.repository.IngredientRepository;
import it.amrito.pastryshopbe.repository.SweetIngredientRepository;
import it.amrito.pastryshopbe.repository.TypologicalRepository;
import it.amrito.pastryshopbe.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TypologicalService {

    @Autowired
    private Mapper mapper;
    @Autowired
    private TypologicalRepository typologicalRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private SweetIngredientRepository sweetIngredientRepository;
    public List<TypologicalSweetLiteDto> findAllDto(){
        List<TypologicalSweetModel> sweetList = typologicalRepository.findAll();
        return mapper.map(sweetList);
    }

    public Optional<TypologicalSweetDto> findDtoById(Long id){
        Optional<TypologicalSweetModel> modelOpt = typologicalRepository.findById(id);
        return modelOpt.map(sweetModel -> mapper.map(sweetModel));
    }

    public Optional<TypologicalSweetModel> findModelById(Long id){
        return typologicalRepository.findById(id);
    }

    public TypologicalSweetLiteDto save(TypologicalSweetDto sweetDto){
        Set<IngredientModel> ingredientModelSet = ingredientRepository.findByIdIn(sweetDto.getIngredientDtoSet().stream()
                .map(IngredientDto::getId).collect(Collectors.toSet()));
        TypologicalSweetModel model = mapper.map(sweetDto, ingredientModelSet);
        TypologicalSweetModel savedModel = typologicalRepository.save(model);
        return mapper.mapLite(savedModel);
    }

    public Optional<TypologicalSweetModel> findModelByNameIgnoreCase(String name){
        return typologicalRepository.findByNameIgnoreCase(name);
    }

    @Transactional
    public TypologicalSweetLiteDto update(TypologicalSweetDto typologicalSweetDto, TypologicalSweetModel typologicalSweetModel){
        //ottengo tutti gli ingredienti associati alla tipologica da modificare
        Set<SweetIngredientModel> sweetIngredientModelSet = typologicalSweetModel.getSweetIngredientModelSet();

        //ottengo una mappa di utility, dove key = idIngrediente inserito nel dto, e value = quantita inserita nel dto.
        Map<Long,Double> ingredientDtoMap = typologicalSweetDto.getIngredientDtoSet()
                .stream()
                .collect(Collectors.toMap(IngredientDto::getId, IngredientDto::getQuantity));

        //calcolo gli ingredienti a cui devo solo aggiornare la quantita
        //calcolo l'intersezione tra l'insieme degli ingredienti inseriti nel dto, e l'insieme degli ingredienti esistenti associati al modello tipologica
        //dal risultato mi aggiorno la quantita
        sweetIngredientModelSet.forEach(ingredientModel -> {
            Double quantity = ingredientDtoMap.get(ingredientModel.getId().getIngredientId());
            if( quantity != null){
                ingredientModel.setQuantity(quantity);
            }
        });

        //calcolo ingredienti da rimuovere
        //l'insieme da rimuovere EQUALS l'insieme degli ingredienti esistenti associati alla tipologica MINUS l'insieme degli ingredienti inseriti nel dto
        Set<SweetIngredientId> ingredientToRemoveSet = sweetIngredientModelSet.stream()
                .map(SweetIngredientModel::getId)
                .filter(id -> !ingredientDtoMap.containsKey(id.getIngredientId()))
                .collect(Collectors.toSet());
        if(!ingredientToRemoveSet.isEmpty()) {
            Set<SweetIngredientModel> sweetIngredientRemoved = sweetIngredientRepository.deleteByIdIn(ingredientToRemoveSet);
            sweetIngredientModelSet.removeAll(sweetIngredientRemoved);
            typologicalSweetModel.setSweetIngredientModelSet(sweetIngredientModelSet);
        }

        //calcolo ingredienti da aggiungere
        //l'insieme result EQUALS l'insieme degli ingredienti inseriti nel dto MINUS l'insieme degli ingredienti esistenti associati alla tipologica
        Set<Long> ingredientFromModelSet = sweetIngredientModelSet.stream()
                .map(x->x.getId().getIngredientId())
                .collect(Collectors.toSet());
        Set<Long> ingredientsToAddSet = typologicalSweetDto.getIngredientDtoSet().stream()
                .map(IngredientDto::getId)
                .filter(x -> !ingredientFromModelSet.contains(x))
                .collect(Collectors.toSet());

        //ottengo gli ingredienti da aggiungere e li setto nella tipologica da modificare
        Set<IngredientModel> ingredientModelSet = new HashSet<>();
        if(!ingredientsToAddSet.isEmpty()){
            ingredientModelSet = ingredientRepository.findByIdIn(ingredientsToAddSet);
        }
        typologicalSweetModel = mapper.map(typologicalSweetDto, ingredientModelSet, typologicalSweetModel);
        //salvo la tipologica
        TypologicalSweetModel savedModel = typologicalRepository.save(typologicalSweetModel);
        return mapper.mapLite(savedModel);

    }

    public void deleteById(Long id){
        typologicalRepository.deleteById(id);
    }
}
