package com.freelancer.controller.admin;

import java.util.*;

import com.freelancer.dto.TransactionDTO;
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
    @RequestMapping(value ="/month",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> month(){
        List<Object[]> objectsList = transactionRepository.finMonth();
        List<TransactionDTO> list = new ArrayList<>();
        for (Object[] obj : objectsList){
            System.out.println(obj);
            TransactionDTO transactionDTO = new TransactionDTO();
            Double price = (Double) obj[0];
            System.out.println(obj[1]);

            Integer month = (Integer) obj[1];

            transactionDTO.setPrice(price);
            transactionDTO.setMonth(month);
           list.add(transactionDTO);
            System.out.println(month);
        }
        System.out.println(list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value ="/day",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> day(@RequestParam(value = "start", required = false) String start, @RequestParam(value = "end", required = false) String end){
       Long startLong,endLong;

        if ((start == null || start.isEmpty()) || (end == null  || end.isEmpty())){
            startLong = DateUtil.getLongStartOfMonth();
            endLong = DateUtil.getTimeLongCurrent();
        }else {
             startLong = DateUtil.setStartDayLong(start + " 00:00:00");
             endLong = DateUtil.setStartDayLong(end + " 23:59:59");
        }
        List<Object[]> objects = transactionRepository.finDay(startLong,endLong);
        List<TransactionDTO> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        for (Object[] obj : objects){
            TransactionDTO transactionDTO = new TransactionDTO();
            Double price = (Double) obj[0];
            Date day = (Date) obj[1];
            calendar.setTime(day);
            transactionDTO.setPrice((20*price)/80);
            transactionDTO.setDay(calendar.get(Calendar.DAY_OF_MONTH));
            list.add(transactionDTO);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
