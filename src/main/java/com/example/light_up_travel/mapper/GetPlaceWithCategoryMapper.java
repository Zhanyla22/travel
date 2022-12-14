package com.example.light_up_travel.mapper;

import com.example.light_up_travel.dto.GetPlaceWithCategDTO;
import com.example.light_up_travel.entity.PlaceCategories;
import com.example.light_up_travel.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component

public class GetPlaceWithCategoryMapper {


    public final CategoryRepository categoryRepository;

    public GetPlaceWithCategoryMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public  static GetPlaceWithCategDTO PlaceEntityToPlaceDTO(PlaceCategories placeCategories, Double rate){
        GetPlaceWithCategDTO getPlaceWithCategDTO = new GetPlaceWithCategDTO();
        getPlaceWithCategDTO.setName(placeCategories.getPlace().getName());
        getPlaceWithCategDTO.setCategoryName(placeCategories.getCategory().getCategoryName());
        getPlaceWithCategDTO.setCategoryId(placeCategories.getCategory().getId());
        getPlaceWithCategDTO.setParentId(placeCategories.getCategory().getParentId());
       // getPlaceWithCategDTO.setParentName(categoryRepository.getParentName(placeCategories.getCategory().getParentId()));
        getPlaceWithCategDTO.setMainFilePath(placeCategories.getPlace().getMainFilePath());
        getPlaceWithCategDTO.setRate(rate);

        return getPlaceWithCategDTO;

    }
}
