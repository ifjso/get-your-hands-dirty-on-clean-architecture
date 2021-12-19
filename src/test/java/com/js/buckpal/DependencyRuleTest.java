package com.js.buckpal;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.js.buckpal.archunit.HexagonalArchitecture;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

class DependencyRuleTest {

    @Test
    void validateRegistrationContextArchitecture() {
        HexagonalArchitecture.boundedContext("com.js.buckpal.account")
            .withDomainLayer("domain")
            .withAdaptersLayer("adapter")
            .incoming("in.web")
            .outgoing("out.persistence")
            .and()
            .withApplicationLayer("application")
            .services("service")
            .incomingPorts("port.in")
            .outgoingPorts("port.out")
            .and()
            .withConfiguration("configuration")
            .check(new ClassFileImporter().importPackages("com.js.buckpal.."));
    }

    @Test
    void testPackageDependencies() {
        noClasses()
            .that()
            .resideInAPackage("com.js.buckpal.account.domain..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("com.js.buckpal.account.application..")
            .check(new ClassFileImporter().importPackages("com.js.buckpal.."));
    }
}
