package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EmployeeImportance {
    public int getImportance(List<Employee> employees, int id) {
        int N = employees.size();
        HashMap<Integer, Employee> map = new HashMap<>();
        for (int i = 0; i < N; i++)
            map.put(employees.get(i).id, employees.get(i));
        return dfs(id, map);

    }

    private int dfs(int employeeId, HashMap<Integer, Employee> map) {
        Employee employee = map.get(employeeId);
        List<Integer> subordinates = employee.subordinates;
        int employeeImportance = employee.importance;
        for (Integer subordinateId : subordinates) {
            employeeImportance += dfs(subordinateId, map);
        }
        return employeeImportance;
    }

    static class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;

        public Employee(int id, int importance, List<Integer> subordinates) {
            this.id = id;
            this.importance = importance;
            this.subordinates = subordinates;
        }
    }

    public static void main(String[] args) {
        EmployeeImportance ei = new EmployeeImportance();
        Employee e1 = new Employee(1, 5, Arrays.asList(2, 3));
        Employee e2 = new Employee(2, 3, new ArrayList<>());
        Employee e3 = new Employee(3, 3, new ArrayList<>());
        ei.getImportance(Arrays.asList(e1, e2, e3), 1);
    }
}
