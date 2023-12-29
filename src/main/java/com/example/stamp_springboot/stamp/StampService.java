package com.example.stamp_springboot.stamp;

import com.example.stamp_springboot.dto.StampAddDto;
import com.example.stamp_springboot.dto.StampDto;
import com.example.stamp_springboot.model.*;
import com.example.stamp_springboot.repository.ImageRepository;
import com.example.stamp_springboot.repository.ShopRepository;
import com.example.stamp_springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StampService {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ImageRepository imageRepository;

    public String addStamp(StampAddDto stampAddDto) throws Exception {
        try {
            Optional<ShopModel> shopModel = shopRepository.findByBusinessNumber(stampAddDto.getBusinessNumber());
            if(shopModel.isPresent()) {
                Optional<UserModel> userModel = userRepository.findByPhoneNumber(stampAddDto.getPhoneNumber());
                if(userModel.isPresent()) {
                    List<StampModel> stampModelList = userModel.get().getStamps();
                    for (StampModel stampModel : stampModelList) {
                        if (Objects.equals(stampModel.getShop_id().getShop_id(), shopModel.get().getShop_id())) {
                            StampModel stampModel1 = new StampModel(stampModel.getShop_id(),stampModel.getCount());
                            Integer stampLimit = shopModel.get().getStamp_limit();
                            if ((stampModel.getCount()+1)%stampLimit  == 0) {
                                stampModel1.resetCount();
                                UUID couponCode = UUID.randomUUID();
                                CouponModel couponModel = new CouponModel(String.valueOf(couponCode), shopModel.get(), shopModel.get().getCoupon_category(), shopModel.get().getCoupon_description());
                                List<CouponModel> couponModels = userModel.get().getCoupons();
                                couponModels.add(couponModel);
                                userModel.get().setCoupons(couponModels);
                                stampModelList.replaceAll(stampModel2 -> stampModel2 == stampModel ? stampModel1 : stampModel2);
                                userModel.get().setStamps(stampModelList);
                                userRepository.save(userModel.get());
                                log.info("addStamp : stamp plus");
                                return "stamp plus, coupon create";
                            } else {
                                stampModel1.plusCount();
                            }
                            stampModelList.replaceAll(stampModel2 -> stampModel2 == stampModel ? stampModel1 : stampModel2);
                            userModel.get().setStamps(stampModelList);
                            userRepository.save(userModel.get());
                            log.info("addStamp : stamp plus");
                            return "stamp plus";
                        }
                    }
                    StampModel stampModel = new StampModel(shopModel.get());
                    stampModelList.add(stampModel);
                    userModel.get().setStamps(stampModelList);
                    userRepository.save(userModel.get());
                    log.info("addStamp : stamp create");
                    return "stamp create";
                }
                log.error("addStamp : User Not Found");
                throw new Exception("User Not Found");
            }
            log.error("addStamp : Shop Not Found");
            throw new Exception("Shop Not Found");
        } catch (Exception err) {
            log.error(String.valueOf(err));
            throw err;
        }
    }

    public List<StampDto> getStampList(String phoneNumber) throws Exception {
        try {
            Optional<UserModel> user = this.userRepository.findByPhoneNumber(phoneNumber);
            if(user.isPresent()) {
                log.info("getStampList : Success");
                List<StampDto> stampDtoList  = new ArrayList<>();
                for(StampModel stampModel : user.get().getStamps()) {
                    StampDto stampDto = new StampDto();
                    stampDto.setCount(stampModel.getCount());
                    stampDto.setShopId(stampModel.getShop_id());
                    Optional<ImageModel> imageModel = imageRepository.findImageModelByBusinessNumber(stampModel.getShop_id().getBusinessNumber());
                    if(imageModel.isPresent()) {
                        stampDto.setImage(imageModel.get().getImage());
                    } else {
                        Optional<ImageModel> basicImage = imageRepository.findImageModelByBusinessNumber("기본이미지");
                        basicImage.ifPresent(model -> stampDto.setImage(model.getImage()));
                    }
                    stampDtoList.add(stampDto);
                }
                return stampDtoList;
            }
            log.error("getStampList : User Not Found");
            throw new Exception("User Not Found");
        } catch (Exception err) {
            log.error(String.valueOf(err));
            throw err;
        }
    }

    public StampDto getStamp(String phoneNumber, String businessNumber) throws Exception {
        try {
            Optional<UserModel> userModel = userRepository.findByPhoneNumber(phoneNumber);
            Optional<ShopModel> shopModel = shopRepository.findByBusinessNumber(businessNumber);
            if(userModel.isPresent() && shopModel.isPresent()) {
                UserModel user = userModel.get();
                List<StampModel> stampList = user.getStamps();
                Optional<StampModel> stampModel = findStamp(stampList,businessNumber);
                if(stampModel.isPresent()) {
                    StampDto stampDto = new StampDto();
                    stampDto.setCount(stampModel.get().getCount());
                    stampDto.setShopId(stampModel.get().getShop_id());
                    Optional<ImageModel> imageModel = imageRepository.findImageModelByBusinessNumber(businessNumber);
                    if(imageModel.isPresent()) {
                        stampDto.setImage(imageModel.get().getImage());
                    } else {
                        Optional<ImageModel> basicImage = imageRepository.findImageModelByBusinessNumber("기본이미지");
                        basicImage.ifPresent(model -> stampDto.setImage(model.getImage()));
                    }
                    log.info("getStamp : Success");
                    return stampDto;
                }
                log.error("getStamp : Not Found Stamp");
                throw new Exception("Not Found Stamp");
            }
            log.error("getStamp : Not Found User or Shop");
            throw new Exception("Not Found User or Shop");
        } catch (Exception err) {
            log.error(String.valueOf(err));
            throw err;
        }
    }

    public String deleteStamp(String phoneNumber, String businessNumber) throws Exception {
        try {
            Optional<UserModel> userModel = userRepository.findByPhoneNumber(phoneNumber);
            Optional<ShopModel> shopModel = shopRepository.findByBusinessNumber(businessNumber);
            if(userModel.isPresent() && shopModel.isPresent()) {
                UserModel user = userModel.get();
                List<StampModel> stampList = user.getStamps();
                Optional<StampModel> stampModel = findStamp(stampList,businessNumber);
                if(stampModel.isPresent()) {
                    stampList.remove(stampModel.get());
                    userModel.get().setStamps(stampList);
                    userRepository.save(userModel.get());
                    log.info("deleteStamp : Success");
                    return "success";
                }
                log.error("deleteStamp : Not Found Stamp");
                throw new Exception("Not Found Stamp");
            }
            log.error("deleteStamp : Not Found User or Shop");
            throw new Exception("Not Found User or Shop");
        } catch (Exception err) {
            log.error(String.valueOf(err));
            throw err;
        }
    }

    private Optional<StampModel> findStamp(List<StampModel> stampList, String businessNumber) {
        for(StampModel stampModel : stampList) {
            if(Objects.equals(stampModel.getShop_id().getBusinessNumber(), businessNumber)) {
                return Optional.of(stampModel);
            }
        }
        return Optional.empty();
    }
}
