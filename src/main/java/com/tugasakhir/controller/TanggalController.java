package com.tugasakhir.controller;

import com.tugasakhir.configuration.Response;
import com.tugasakhir.dao.tanggal.TanggalDao;
import com.tugasakhir.model.BuildTanggal;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Tag(name = "TANGGAL")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
public class TanggalController {

    private final TanggalDao dao;
    @GetMapping("/buildtanggal")
    private ResponseEntity<?> getTanggal(
        @RequestParam String bulan,
        @RequestParam String tahun
    ){
        String slice = tahun + "-" + bulan + "-" + "01";
        Date date1 = null;
        try{
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(slice);
        } catch (Exception e){
            e.printStackTrace();
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        assert date1 != null;
        int cekHari = date1.getDay(); // 0 1 2 3 4 5 6

        YearMonth yearMonthObject = YearMonth.of(2023, (Integer.parseInt(bulan)-1) == 0? 12: Integer.parseInt(bulan)-1);
        int totalHariSebelumnya = yearMonthObject.lengthOfMonth();

        YearMonth yearMonthObject2 = YearMonth.of(2023, Integer.parseInt(bulan));
        int totalHariBulanIni = yearMonthObject2.lengthOfMonth();

        List<BuildTanggal> buildTanggals = dao.beforeMonth(totalHariSebelumnya, cekHari);
        List<BuildTanggal> buildTanggals1 = dao.thisMonth(totalHariBulanIni, bulan, tahun);
        if(buildTanggals1.size() == 0){
            return Response.response("INTERNAL SERVER ERROR", HttpStatus.BAD_REQUEST);
        }
        buildTanggals.addAll(buildTanggals1);
        List<BuildTanggal> buildTanggals2 = dao.afterMonth(buildTanggals.size());
        buildTanggals.addAll(buildTanggals2);

        System.out.println("BULAN " + bulan);
        System.out.println("TAHUN " + tahun);
        int a = 1;
        for(BuildTanggal e : buildTanggals){
            System.out.print(e.getTanggal()+ "\t");
            if(a%7==0){
                System.out.println();
            }
            a++;
        }
        return Response.response(buildTanggals, HttpStatus.OK);
    }
}
