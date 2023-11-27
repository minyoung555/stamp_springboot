package com.example.stamp_springboot.stamp;

import com.example.stamp_springboot.dto.StampAddDto;
import com.example.stamp_springboot.model.CouponModel;
import com.example.stamp_springboot.model.ShopModel;
import com.example.stamp_springboot.model.StampModel;
import com.example.stamp_springboot.model.UserModel;
import com.example.stamp_springboot.repository.ShopRepository;
import com.example.stamp_springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class StampService {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public String addStamp(StampAddDto stampAddDto) throws Exception {
        try {
            Optional<ShopModel> shopModel = shopRepository.findByBusinessNumber(stampAddDto.getBusinessNumber());
            if(shopModel.isPresent()) {
                Optional<UserModel> userModel = userRepository.findByPhoneNumber(stampAddDto.getPhoneNumber());
                if(userModel.isPresent()) {
                    List<StampModel> stampModelList = userModel.get().getStamps();
                    for (StampModel stampModel : stampModelList) {
                        if (stampModel.getShop_id() == shopModel.get()) {
                            stampModel.plusCount();
                            stampModelList.add(stampModel);
                            userModel.get().setStamps(stampModelList);
                            Integer stampLimit = shopModel.get().getStamp_limit();
                            if (stampLimit <= stampModel.getCount() && (stampLimit % stampModel.getCount()) == 0) {
                                CouponModel couponModel = new CouponModel(shopModel.get());
                                List<CouponModel> couponModels = new ArrayList<>();
                                couponModels.add(couponModel);
                                userModel.get().setCoupons(couponModels);
                            }
                            userRepository.save(userModel.get());
                            log.info("addStamp : Success");
                            return "success";
                        }
                    }
                    StampModel stampModel = new StampModel(shopModel.get());
                    stampModelList.add(stampModel);
                    userModel.get().setStamps(stampModelList);
                    userRepository.save(userModel.get());
                    log.info("addStamp : Success");
                    return "success";
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

    public List<StampModel> getStampList(String phoneNumber) throws Exception {
        try {
            Optional<UserModel> user = this.userRepository.findByPhoneNumber(phoneNumber);
            if(user.isPresent()) {
                return user.get().getStamps();
            }
            log.error("getStampList : User Not Found");
            throw new Exception("User Not Found");
        } catch (Exception err) {
            log.error(String.valueOf(err));
            throw err;
        }
    }
}
