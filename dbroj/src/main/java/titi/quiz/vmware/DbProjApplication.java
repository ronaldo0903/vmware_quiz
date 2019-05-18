package titi.quiz.vmware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import titi.quiz.vmware.domain.Service;
import titi.quiz.vmware.dao.ServiceRepository;
import titi.quiz.vmware.domain.User;
import titi.quiz.vmware.dao.UserRepository;

@SpringBootApplication
public class DbProjApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ServiceRepository servRepo;

	public static void main(String[] args) {
		SpringApplication.run(DbProjApplication.class, args);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void run(String... args) throws Exception {
		userRepo.deleteAllInBatch();
		servRepo.deleteAllInBatch();

		User u1 = new User("Bob", 24);
		User u2 = new User("David", 30);
		User u3 = new User("Marry", 19);
		User u4 = new User("Lucy", 26);

		Service s1 = new Service("Sports");
		Service s2 = new Service("Reading");
		Service s3 = new Service("Dancing");

		u1.addService(s1);
		u1.addService(s3);

		u2.addService(s2);

		u3.addService(s2);
		u3.addService(s3);

		u4.addService(s1);
		u4.addService(s2);
		u4.addService(s3);

		/*u1.getServices().add(s1);
		u1.getServices().add(s3);
		s1.getUsers().add(u1);
		s3.getUsers().add(u1);

		u2.getServices().add(s2);
		u2.getServices().add(s3);
		s2.getUsers().add(u2);
		s3.getUsers().add(u2);*/


		userRepo.save(u1);
		userRepo.save(u2);
		userRepo.save(u3);
		userRepo.save(u4);
	}
}
