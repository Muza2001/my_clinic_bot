package academic.com.controller;

import academic.com.dto.request.DiagnosisRequest;
import academic.com.service.DiagnoseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/employee")
public class DiagnosisController {

    private final DiagnoseService diagnoseService;

    @PostMapping("/create")
    public ResponseEntity<?> createDiagnosis(@RequestBody DiagnosisRequest request){
        return ResponseEntity.status(201).body(diagnoseService.save(request));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody DiagnosisRequest request, @PathVariable Long id){
        return ResponseEntity.status(202).body(diagnoseService.edit(request, id));
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(diagnoseService.findById(id));
    }

    @GetMapping("get_list")
    public ResponseEntity<?> findList(){
        return ResponseEntity.status(200).body(diagnoseService.findList());
    }

}
