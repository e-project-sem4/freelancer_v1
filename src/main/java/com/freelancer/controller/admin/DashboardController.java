package com.freelancer.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freelancer.model.Transaction;
import com.freelancer.repository.TransactionRepository;
import com.freelancer.utils.DateUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.freelancer.service.DashboardService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin/dashboard")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;
    @Autowired
    TransactionRepository transactionRepository;
    @GetMapping()
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public HashMap<Integer, Long> showData() {
        return dashboardService.getCountDashboard();
    }
    @GetMapping()
    @RequestMapping(value = "/line-chart")
    public String lineChart(){
        List<Transaction> list = transactionRepository.findAll();
        JsonArray jsonDay = new JsonArray();
        JsonArray jsonPrice = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        list.forEach(data->{
            jsonDay.add(DateUtil.getDayFromLong(data.getCreateAt()));
            jsonPrice.add(data.getPrice());
        });
        jsonObject.add("day", jsonDay);
        jsonObject.add("price", jsonPrice);
        return jsonObject.toString();
    }
    @GetMapping()
    @RequestMapping("/multiplelinechartdata")
    public ResponseEntity<?> getDataForMultipleLine() {
        List<Transaction> dataList = transactionRepository.findAll();
        Map<Transaction.TransactionType, List<Transaction>> mappedData = new HashMap<>();
        for(Transaction data : dataList) {
            if(mappedData.containsKey(data.getType())) {
                mappedData.get(data.getType()).add(data);
            }else {
                List<Transaction> tempList = new ArrayList<Transaction>();
                tempList.add(data);
                mappedData.put(data.getType(), tempList);
            }
        }
        return new ResponseEntity<>(mappedData, HttpStatus.OK);
    }
    @RequestMapping(value ="/hello",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> demo(@RequestParam Long start, @RequestParam Long end){
        List<Map<Long,Transaction>> list = transactionRepository.findRegisteredCustomersHistory(start,end);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
