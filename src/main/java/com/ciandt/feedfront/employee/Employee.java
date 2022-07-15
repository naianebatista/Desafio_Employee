package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.util.*;

import java.io.*;
import java.util.stream.Collectors;

public class Employee implements Serializable, Iterator{
    private String id;
    private String nome;
    private String sobrenome;
    private String email;

    public Employee() {
    }

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    private static final List<Employee> listOfEmployee = new ArrayList<Employee>();

    private static void addNewEmployee(Employee employee)
    {
        listOfEmployee.add(employee);
    }

    public static List<Employee> getList()
    {
        return Collections.unmodifiableList(listOfEmployee);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }


    public void setNome(String nome) throws ComprimentoInvalidoException {
        this.nome = nome;
        if (nome.length() <= 2) {
            throw new ComprimentoInvalidoException("Caution! name must have 3 or more characteres");
        }
    }

    public String getSobrenome() {
        return sobrenome;
    }


    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
        this.sobrenome = sobrenome;
        if (sobrenome.length() <= 2) {
            throw new ComprimentoInvalidoException("Caution! name must have 3 or more characteres");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static boolean saveListEmployee (List<Employee> list){

        String path = "file.txt";

        File file = new File(path);

        if(! file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        try{
            FileOutputStream fileOutput = new FileOutputStream(file);
            ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput);

            objOutput.writeObject(list);

            objOutput.flush();
            fileOutput.flush();

            objOutput.close();
            fileOutput.close();

            return true;

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static boolean salvarEmployee(Employee employee) throws Exception, EmailInvalidoException {

        addNewEmployee(employee);

        return saveListEmployee(listOfEmployee);

    }

    public static Employee atualizarEmployee(Employee employee) throws Exception {

        for (Employee e : listOfEmployee){
            if(e.getId().equals(employee.getId())){
                e.setNome(employee.getNome());
                e.setSobrenome(employee.getSobrenome());
                saveListEmployee(listOfEmployee);
                return buscarEmployee(employee.getId());
            }
        }
        return null;

    }

    public static List<Employee> listarEmployees() throws IOException, ClassNotFoundException {

        String path = "file.txt";

        File file = new File(path);

        try {
            FileInputStream fileInput = new FileInputStream(file);
            ObjectInputStream objInput = new ObjectInputStream(fileInput);

            Object object = objInput.readObject();
            List<Employee> list = (List<Employee>) object;

            objInput.close();
            fileInput.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }

    public static Employee buscarEmployee(String id) throws IOException, EmployeeNaoEncontradoException, ClassNotFoundException, ComprimentoInvalidoException {

        List<Employee> listOfEmployee = listarEmployees();

        for (Employee employee : listOfEmployee) {
            if (id.equals(employee.getId().toString())) {
                return employee;
            } else {
                throw new EmployeeNaoEncontradoException("not found");
            }
        }
        return null;

    }

    public static void apagarEmployee(String id) throws Exception {

        List<Employee> listOfEmployee = listarEmployees();

        ListIterator li = listOfEmployee.listIterator();

        while(li.hasNext()){
            Employee e = (Employee)li.next();
            if(id.equals(e.getId().toString())){
                li.remove();
                saveListEmployee(listOfEmployee);
            }
        }
    }



    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
