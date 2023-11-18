package com.example.stamp_springboot.stamp;

import com.example.stamp_springboot.dto.StampAddDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stamp")
public class StampController {
    private final StampService stampService;

    @PostMapping()
    public String addStamp(@RequestBody StampAddDto stampAddDto) throws Exception {
        return stampService.addStamp(stampAddDto);
    }
}
