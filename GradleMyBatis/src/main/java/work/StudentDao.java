package work;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class StudentDao {
	public List<StudentDto> getStudentList() {

		List<StudentDto> list = new ArrayList<StudentDto>();
		try {
			InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

			SqlSession session = factory.openSession();
			list = session.selectList("sample.mybatis.selectTest");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
