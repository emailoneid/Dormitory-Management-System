    package cz.mendelu.repository;

    import cz.mendelu.domain.Enrollment;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    }
