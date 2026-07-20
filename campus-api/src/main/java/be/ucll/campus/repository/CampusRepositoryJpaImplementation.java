package be.ucll.campus.repository;

import be.ucll.campus.model.Campus;
import be.ucll.campus.repository.jpa.CampusJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class CampusRepositoryJpaImplementation implements CampusRepository {

    private final CampusJpaRepository campusJpaRepository;

    @Autowired
    public CampusRepositoryJpaImplementation(CampusJpaRepository campusJpaRepository) {
        this.campusJpaRepository = campusJpaRepository;
    }

    @Override
    public List<Campus> getCampuses() {
        return campusJpaRepository.findAll();
    }

    @Override
    public Optional<Campus> findCampusByName(String campusName) {
        return campusJpaRepository.findById(campusName);
    }

    @Override
    public Campus saveCampus(Campus campus) {
        return campusJpaRepository.save(campus);
    }

    @Override
    public void deleteCampus(Campus campus) {
        campusJpaRepository.delete(campus);
    }

    @Override
    public void removeAllCampuses() {
        campusJpaRepository.deleteAll();
    }
}
