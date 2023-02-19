package it.amrito.pastryshopbe.utils;

import it.amrito.pastryshopbe.dto.DashboardDto;
import it.amrito.pastryshopbe.dto.IngredientDto;
import it.amrito.pastryshopbe.dto.TypologicalSweetDto;
import it.amrito.pastryshopbe.dto.TypologicalSweetLiteDto;
import it.amrito.pastryshopbe.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {

    @Autowired
    ModelMapper modelMapper;


    public List<TypologicalSweetLiteDto> map(List<TypologicalSweetModel> sweetModelList){
        return sweetModelList.stream()
                .map(model -> modelMapper.map(model, TypologicalSweetLiteDto.class)).toList();
    }

    public TypologicalSweetDto map(TypologicalSweetModel sweetModel){
        TypologicalSweetDto sweetDto = modelMapper.map(sweetModel, TypologicalSweetDto.class);
        for(SweetIngredientModel siModel : sweetModel.getSweetIngredientModelSet()){
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.setId(siModel.getIngredient().getId());
            ingredientDto.setName(siModel.getIngredient().getName());
            ingredientDto.setQuantity(siModel.getQuantity());
            ingredientDto.setUnitMeasure(siModel.getIngredient().getUnitMeasure());
            sweetDto.getIngredientDtoSet().add(ingredientDto);
        }
        return sweetDto;
    }

    public TypologicalSweetLiteDto mapLite(TypologicalSweetModel sweetModel){
        return modelMapper.map(sweetModel, TypologicalSweetLiteDto.class);
    }

    public DashboardDto map(DashboardModel dashboardModel, Double effectivePrice){
        DashboardDto dashboardDto = modelMapper.map(dashboardModel, DashboardDto.class);
        TypologicalSweetLiteDto typologicalSweetLiteDto = this.mapLite(dashboardModel.getTypologicalSweetModel());
        typologicalSweetLiteDto.setPrice(effectivePrice);
        dashboardDto.setTypologicalSweetDto(typologicalSweetLiteDto);
        return dashboardDto;
    }

    public DashboardDto map(TypologicalSweetLiteDto sweetLiteDto, DashboardModel dashboardModel){
        DashboardDto dashboardDto = modelMapper.map(dashboardModel, DashboardDto.class);
        dashboardDto.setTypologicalSweetDto(sweetLiteDto);
        return dashboardDto;
    }

    public TypologicalSweetModel map(TypologicalSweetDto sweetDto, Set<IngredientModel> ingredientModelSet){
        TypologicalSweetModel typologicalSweetModel = modelMapper.map(sweetDto, TypologicalSweetModel.class);
        Set<SweetIngredientModel> sweetIngredientModelSet = new HashSet<>();
        for(IngredientModel ingredientModel: ingredientModelSet){
            SweetIngredientModel sweetIngredientModel = new SweetIngredientModel();
            sweetIngredientModel.setId(new SweetIngredientId());
            sweetIngredientModel.setIngredient(ingredientModel);
            sweetIngredientModel.setTypologicalSweet(typologicalSweetModel);
            sweetIngredientModel.setQuantity(sweetDto.getIngredientDtoSet().stream()
                    .filter(x -> Objects.equals(x.getId(), ingredientModel.getId()))
                    .findFirst().orElseThrow().getQuantity());
            sweetIngredientModelSet.add(sweetIngredientModel);
        }

        typologicalSweetModel.setSweetIngredientModelSet(sweetIngredientModelSet);
        return typologicalSweetModel;
    }

    public TypologicalSweetModel map(TypologicalSweetDto sweetDto, Set<IngredientModel> ingredientModelSet,
                                      TypologicalSweetModel typologicalSweetModel){
        for(IngredientModel ingredientModel: ingredientModelSet){
            SweetIngredientModel sweetIngredientModel = new SweetIngredientModel();
            sweetIngredientModel.setId(new SweetIngredientId());
            sweetIngredientModel.setIngredient(ingredientModel);
            sweetIngredientModel.setTypologicalSweet(typologicalSweetModel);
            sweetIngredientModel.setQuantity(sweetDto.getIngredientDtoSet().stream()
                    .filter(x -> Objects.equals(x.getId(), ingredientModel.getId()))
                    .findFirst().orElseThrow().getQuantity());
            typologicalSweetModel.getSweetIngredientModelSet().add(sweetIngredientModel);
        }
        typologicalSweetModel.setName(sweetDto.getName());
        typologicalSweetModel.setPrice(sweetDto.getPrice());
        typologicalSweetModel.setDescription(sweetDto.getDescription());
        return typologicalSweetModel;
    }
}
