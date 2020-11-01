package com.zqy.service;

import com.zqy.dao.ScoreDao;
import com.zqy.entity.Score;
import com.zqy.utils.BeanMapUtils;
import com.zqy.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    @Autowired
    private ScoreDao scoreDao;

    public int create(Score pi) {
        return scoreDao.create(pi);
    }

    public int create(String sectionIds,String courseIds,Integer studentId) {

        scoreDao.delete(MapParameter.getInstance().add("stuId",studentId).getMap());

        String[] sectionIdArr = sectionIds.split(",");
        String[] courseIdArr = courseIds.split(",");
        int flag = 0;
        for (int i=0;i<sectionIdArr.length;i++) {
            Score score = new Score();
            score.setSectionId(Integer.parseInt(sectionIdArr[i]));
            score.setCourseId(Integer.parseInt(courseIdArr[i]));
            score.setStuId(studentId);
            flag = scoreDao.create(score);
        }
        return flag;
    }


    public int delete(Integer id) {
        return scoreDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    public int update(Score score) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(score)).addId(score.getId()).getMap();
        return scoreDao.update(map);
    }

    public int update(Integer courseId,Integer sectionId,String stuIds,String scores) {
        String[] stuIdArray = stuIds.split(",");
        String[] scoresArray = scores.split(",");
        int flag = 0;
        for (int i=0;i<stuIdArray.length;i++) {
            Map<String, Object> map = MapParameter.getInstance()
                    .add("courseId", courseId)
                    .add("sectionId", sectionId)
                    .add("stuId", Integer.parseInt(stuIdArray[i]))
                    .add("updateScore", Integer.parseInt(scoresArray[i])).getMap();
            flag = scoreDao.update(map);
        }
        return flag;
    }

    public List<Score> query(Score score) {
        return scoreDao.query(BeanMapUtils.beanToMap(score));
    }

    public Score detail(Integer id) {
        return scoreDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    public int count(Score score) {
        return scoreDao.count(BeanMapUtils.beanToMap(score));
    }

    public List<HashMap> queryAvgScoreBySection(){
        return scoreDao.queryAvgScoreBySection(null);
    }

    public List<HashMap> queryScoreByStudent(Integer studentId){
        Map<String, Object> map = MapParameter.getInstance().add("studentId", studentId).getMap();
        return scoreDao.queryScoreByStudent(map);
    }

}
