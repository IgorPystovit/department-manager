package com.view;

import com.entities.Department;
import com.service.DepartmentService;
import com.service.LectorService;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class DepartmentManagerMenu extends Menu {

    private DepartmentService departmentService;

    private LectorService lectorService;

    public DepartmentManagerMenu(DepartmentService departmentService, LectorService lectorService) {
        this.departmentService = departmentService;
        this.lectorService = lectorService;
    }

    @Override
    public Map<Integer, String> initializeItems() {
        Map<Integer, String> menuItems = new LinkedHashMap<>();

        menuItems.put(1, "Print all departments");
        menuItems.put(2, "Get head of department");
        menuItems.put(3, "Get department statistic");
        menuItems.put(4, "Get average salary");
        menuItems.put(5, "Get employee count");
        menuItems.put(6, "Search lectors by");

        return menuItems;
    }

    @Override
    public Map<Integer, Runnable> initializeActions() {
        Map<Integer, Runnable> menuActions = new LinkedHashMap<>();

        Consumer<Department> getHeadOfDepartment =
                department -> log.info("Head of {} department is {}", department.getName(), department.getHead().getName());

        Consumer<Department> getDepartmentStatistic =
                department -> log.info("Department statistic: {}", departmentService.getDepartmentStatistic(department));

        Consumer<Department> getDepartmentAverageSalary =
                department -> log.info("The average salary of {} is {}",
                        department.getName(), departmentService.getDepartmentAverageSalary(department));

        Consumer<Department> getEmployeeCount =
                department -> log.info("{}", department.getLectors().size());

        menuActions.put(2, () -> readDepartmentNameAndPerform(getHeadOfDepartment));
        menuActions.put(1,
                () -> departmentService.getAllDepartments().forEach(department -> log.info("{}", department.getName())));
        menuActions.put(3, () -> readDepartmentNameAndPerform(getDepartmentStatistic));
        menuActions.put(4, () -> readDepartmentNameAndPerform(getDepartmentAverageSalary));
        menuActions.put(5, () -> readDepartmentNameAndPerform(getEmployeeCount));
        menuActions.put(6, () -> {
            log.info("Input template");
            String template = ReaderUtils.readString();
            lectorService
                    .getAllLectorsByNameTemplate(template)
                    .forEach(lector -> log.info("{}", lector.getName()));
        });

        return menuActions;
    }

    private void readDepartmentNameAndPerform(Consumer<Department> action) {
        log.info("Input department name");
        String departmentName = ReaderUtils.readString();
        Optional<Department> optionalDepartment = departmentService.getDepartmentByName(departmentName);

        if (optionalDepartment.isPresent()) {
            action.accept(optionalDepartment.get());
        } else {
            log.info("No department for name {}", departmentName);
        }
    }
}
