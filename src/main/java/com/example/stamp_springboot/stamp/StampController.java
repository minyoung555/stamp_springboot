package com.example.stamp_springboot.stamp;

import com.example.stamp_springboot.dto.StampAddDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name="Stamp", description = "스탬프 관련 기능")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stamp")
public class StampController {
    private final StampService stampService;

    @Operation(
            operationId = "Stamp 추가",
            summary = "Stamp를 추가합니다.",
            description = "기존에 스탬프가 존재할 경우 스탬프 값을 더하고, 없으면 새로운 스탬프를 생성합니다. 스탬프가 일정값에 도달하면 쿠폰을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스탬프 추가 성공")
            }
    )
    @PostMapping()
    public String addStamp(
            @RequestBody StampAddDto stampAddDto
    ) throws Exception {
        return stampService.addStamp(stampAddDto);
    }
}
