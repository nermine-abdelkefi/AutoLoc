
package tn.esprit.autoloc;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.ModePaiement;
import tn.esprit.autoloc.repository.IContratRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ContratCascadeTest {

    @Autowired
    private IContratRepository contratRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testerCascadeEtOrphanRemoval() {

        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(
                new BigDecimal("200.00"));
        contrat.setValide(true);

        Paiement paiement = new Paiement();
        paiement.setMontant(
                new BigDecimal("200.00"));
        paiement.setDatePaiement(LocalDate.now());
        paiement.setModePaiement(ModePaiement.CARTE);

        // Synchroniser les deux côtés
        paiement.setContrat(contrat);
        contrat.getPaiements().add(paiement);

        // Tester CascadeType.ALL (PERSIST)
        contratRepository.saveAndFlush(contrat);

        Long paiementId = paiement.getIdPaiement();
        assertNotNull(paiementId);

        // Tester orphanRemoval
        contrat.getPaiements().remove(paiement);
        paiement.setContrat(null);

        entityManager.flush();
        entityManager.clear();

        assertNull(entityManager.find(
                Paiement.class, paiementId));
    }
}
