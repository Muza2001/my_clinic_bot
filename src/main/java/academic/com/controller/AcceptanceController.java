package academic.com.controller;

import academic.com.dto.request.AcceptanceRequest;
import academic.com.service.AcceptanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/acceptance")
public class AcceptanceController {

    private final AcceptanceService acceptanceService;

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody AcceptanceRequest request){
        return ResponseEntity.status(201).body(acceptanceService.register(request));
    }

    @GetMapping("/find_by_id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(acceptanceService.findById(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, AcceptanceRequest acceptanceRequest){
        return ResponseEntity
                .status(203)
                .body(acceptanceService
                        .editAcceptance(id,acceptanceRequest));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ResponseEntity.status(204).body(acceptanceService.deleteById(id));
    }
}
