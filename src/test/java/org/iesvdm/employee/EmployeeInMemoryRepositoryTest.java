package org.iesvdm.employee;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Test doubles that are "fakes" must be tested
 *
 *
 */
public class EmployeeInMemoryRepositoryTest {

	private EmployeeInMemoryRepository employeeRepository;

	private List<Employee> employees;

	@BeforeEach
	public void setup() {
		employees = new ArrayList<>();
		employeeRepository = new EmployeeInMemoryRepository(employees);
	}

	/**
	 * Descripcion del test:
	 * crea 2 Employee diferentes
	 * aniadelos a la coleccion de employees
	 * comprueba que cuando llamas a employeeRepository.findAll
	 * obtienes los empleados aniadidos en el paso anterior
	 */
	@Test
	public void testEmployeeRepositoryFindAll() {
		EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

		Employee employee1 = new Employee("Juan Pérez", 10);
		Employee employee2 = new Employee("Ana Gómez", 8);

		List<Employee> employees = Arrays.asList(employee1, employee2);

		Mockito.when(employeeRepository.findAll()).thenReturn(employees);

		List<Employee> result = employeeRepository.findAll();

		assertEquals(2, result.size());
		assertTrue(result.contains(employee1));
		assertTrue(result.contains(employee2));
	}


	/**
	 * Descripcion del test:
	 * salva un Employee mediante el metodo
	 * employeeRepository.save y comprueba que la coleccion
	 * employees contiene solo ese Employee
	 */
	@Test
	public void testEmployeeRepositorySaveNewEmployee() {
		EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

		Employee employee = new Employee("Juan", 10);

		Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

		Employee result = employeeRepository.save(employee);

		assertEquals(employee, result);
	}

	/**
	 * Descripcion del tets:
	 * crea un par de Employee diferentes
	 * aniadelos a la coleccion de employees.
	 * A continuacion, mediante employeeRepository.save
	 * salva los Employee anteriores (mismo id) con cambios
	 * en el salario y comprueba que la coleccion employees
	 * los contiene actualizados.
	 */
	@Test
	public void testEmployeeRepositorySaveExistingEmployee() {
		EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

		Employee employee1 = new Employee("Juan",  5000);
		Employee employee2 = new Employee("Ana",  6000);

		Mockito.when(employeeRepository.save(employee1)).thenAnswer(invocation -> {
			Employee savedEmployee = invocation.getArgument(0);
			savedEmployee.setSalary(5500);
			return savedEmployee;
		});
		Mockito.when(employeeRepository.save(employee2)).thenAnswer(invocation -> {
			Employee savedEmployee = invocation.getArgument(0);
			savedEmployee.setSalary(6500);
			return savedEmployee;
		});

		Employee result1 = employeeRepository.save(employee1);
		Employee result2 = employeeRepository.save(employee2);

		assertEquals(5500, result1.getSalary());
		assertEquals(6500, result2.getSalary());
	}
}
