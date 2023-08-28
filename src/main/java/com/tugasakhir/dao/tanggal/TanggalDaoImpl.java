package com.tugasakhir.dao.tanggal;

import com.tugasakhir.configuration.DBHandler;
import com.tugasakhir.model.BuildTanggal;
import com.tugasakhir.util.Helpers;
import com.tugasakhir.util.ObjectMapper;
import com.tugasakhir.util.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TanggalDaoImpl extends DBHandler implements TanggalDao {
    public TanggalDaoImpl(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public List<BuildTanggal> beforeMonth(Integer totalHariBulanSebelumnya, Integer day){
        List<BuildTanggal> buildTanggals = new ArrayList<>();
        for(int a = day; a > 0; a--){
            BuildTanggal buildTanggal = new BuildTanggal();
            buildTanggal.setTanggal(totalHariBulanSebelumnya - a + 1);
            buildTanggal.setActive(false);
            buildTanggal.setFlag("DISABLE");
            buildTanggals.add(buildTanggal);
        }
        return buildTanggals;
    }

    @Override
    public List<BuildTanggal> thisMonth(Integer totalHariBulanIni, String bulan, String tahun) {
        try {
            Object[] obj = {bulan, tahun};
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre("func_tanggal_get", obj, new Mapper());
            List<BuildTanggal> buildTanggals = new ArrayList<>();
            for(int a = 1; a <= totalHariBulanIni; a++) {
                BuildTanggal buildTanggal = new BuildTanggal();
                buildTanggal.setTanggal(a);
                buildTanggal.setActive(true);
                buildTanggal.setFlag("GREY");
                for(LinkedHashMap<String, String> e : linkedHashMaps){
                    int tanggal = Integer.parseInt(e.get("tanggal"));
                    if(tanggal == a){
                        buildTanggal.setFlag(e.get("flagging"));
                        break;
                    } else if(tanggal >= a){
                        break;
                    }
                }
                buildTanggals.add(buildTanggal);
            }
            return buildTanggals;
        } catch (RuntimeException e){
            return new ArrayList<>();
        }
    }


    public List<BuildTanggal> afterMonth(Integer totalSize){
        List<BuildTanggal> buildTanggals = new ArrayList<>();
        int test =totalSize%7;
        for(int a = 7, b = 1; a > test; a--, b++){
            BuildTanggal buildTanggal = new BuildTanggal();
            buildTanggal.setTanggal(b);
            buildTanggal.setActive(false);
            buildTanggal.setFlag("DISABLE");
            buildTanggals.add(buildTanggal);
        }
        return buildTanggals;
    }

}
