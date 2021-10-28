package com.freelancer.controller.admin;

import java.util.*;

import com.freelancer.dto.TransactionDTO;
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
    @RequestMapping(value ="/month",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> month(){
        List<Object[]> objectsList = transactionRepository.finMonth();
        List<TransactionDTO> list = new ArrayList<>();
        for (Object[] obj : objectsList){
            TransactionDTO transactionDTO = new TransactionDTO();
            Double price = (Double) obj[0];
            Integer month = (Integer) obj[1];

            transactionDTO.setPrice(price);
            transactionDTO.setMonth(month);
           list.add(transactionDTO);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @RequestMapping(value ="/day",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> day(){
        List<Object[]> objects = transactionRepository.finDay();
        List<TransactionDTO> list = new ArrayList<>();
        for (Object[] obj : objects){
            TransactionDTO transactionDTO = new TransactionDTO();
            Double price = (Double) obj[0];
            Date day = (Date) obj[1];

            transactionDTO.setPrice((20*price)/80);
            transactionDTO.setDay(day);
            list.add(transactionDTO);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
