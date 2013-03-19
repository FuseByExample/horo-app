package com.fusesource.examples.horo.db.impl;

import com.fusesource.examples.horo.db.HoroscopeDAO;
import com.fusesource.examples.horo.model.Horoscope;
import com.fusesource.examples.horo.model.StarSign;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author WimV
 */
public class HoroscopeDAOImpl implements HoroscopeDAO {

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }


    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List<Horoscope> getHoroscopesBySign(StarSign sign){

        SqlSession session = sqlSessionFactory.openSession();
        List<Horoscope> horoscopes;

        try {
           horoscopes = session.selectList("selectHoroscopesBySign",sign);
        } finally {
            session.close();
        }


        return horoscopes;
    }
}
