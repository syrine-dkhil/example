# Introduction to Java EE
A quick introduction to building a JSF application using JSF, CDI and JPA.

## Overview
Introduction to the Java EE stack, including JSF, CDI and JPA.

* JSF - Java Server Faces
  * Java specification for component based web UI development
  * Secure
  * Rich UI components
  * Rapid development
* CDI - Context Dependency Injection
  * Dependency Injection (DI) framework with scoping and lifecycle management
  * Annotation driven with minimal XML
  * Plain old Java objects
* JPA - Java Persistence API
  * Object Relational Mapping (ORM) specification
  * Persistence
  * Secure
  * Portable

## The Maven Project
The project used in this video is a basic maven project.  The following files are
worth mentioning:

* persistence.xml - JPA configuration file
  * Defines persistent units
  * Persistence units define classes to be managed by unit
  * Datasource or JDBC connection
  * Dialect or database type
  * Proprietary properties as necessary
* beans.xml - CDI configuration file
  * Presence of file in a jar or war indicates the archive is a beans archive.
  * Triggers scanning for CDI beans
  * Dependency jars in lib of war are scanned for beans.xml as well
  * can be used to define interceptors, alternatives, etc
  * Otherwise rarely used
* web.xml - Web archive deployment descriptor
  * Not required with Servlet 3.0, but configuration is best in XML
  * Used in example to associate and add .xhtml files to welcome list

# Employees Page
Let's build our landing page.

## Index.xhtml
Why XHTML?
* Combines XML and HTML resulting in well-formed extensible HTML documents
* Forces developers to be precise making code easier to maintain
* Works with XSL
* Consistent rules
* Extensible

## Namespaces
Namespaces are used to identify tag libraries (components) that used by the page
or composite component. Think imports for facelet XHTML pages.

Let's add namespaces to the index.xhtml
```html
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html">
<h:head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Example</title>
</h:head>
<h:body>
    <p>Hello World!</p>
</h:body>
</html>
```

## Create the Employee JPA Entity
Let's start with our model and create a JPA Entity to represent an Employee in
our application.

```java
package com.example;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The employee JPA entity.
 */
@Entity
public class Employee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_no")
	private Integer employeeNo;

	@Column(name = "first_name", length = 50)
	private String firstName;

	@Column(name = "last_name", length = 50)
	private String lastName;

	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Column(name = "hire_date")
	@Temporal(TemporalType.DATE)
	private Date hireDate;

	public Integer getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(final Integer employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(final Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(final Date hireDate) {
		this.hireDate = hireDate;
	}
}
```

## A Controller and a Query
Now let's create a controller for our page that will query for Employees and
return a List of employees to display.

```java
package com.example.ui.employee;

import com.example.Employee;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Controller for the Employee UI page
 */
@RequestScoped
@Named
public class EmployeeController {

	@PersistenceContext
	EntityManager entityManager;

	public List<Employee> getEmployees() {
		TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e", Employee.class);
		return query.getResultList();
	}
}
```

## Display our Employees
Let's display our employees.

```html
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html">
<h:head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Employees</title>
</h:head>
<h:body>
    <p>Employees</p>
    <ul>
        <ui:repeat value="#{employeeController.employees}" var="emp">
            <li>#{emp.employeeNo} - #{emp.firstName} #{emp.lastName}</li>
        </ui:repeat>
    </ul>
</h:body>
</html>
```

## CDI Concept: Produces
In the previous bean, we named the controller and referenced it by name directly.
But if we wanted to simply produce the list of employees and bind directly to
the list by name, we could do that as well:

```java
@Produces
@Named
public List<Employee> getEmployees() {
}
```

We can now reference the employees list by name:

```html
<ui:repeat value="#{employeeController.employees}" var="emp">
```

## Aren't tables better?
Let's put them into an HTML Table:

```html
    <h:dataTable value="#{employees}" var="emp">
        <h:column>#{emp.employeeNo}</h:column>
        <h:column>#{emp.firstName}</h:column>
        <h:column>#{emp.lastName}</h:column>
        <h:column>#{emp.dateOfBirth}</h:column>
        <h:column>#{emp.hireDate}</h:column>        
    </h:dataTable>
```

## Format Date
Components are built in a View Tree and they can have children.  Some of those
perform functions, like converting or formatting data depending on the
component they are nested in.  Let's add a namespace for the JSF core namespace
which includes functions and components that are independent of the RenderKit.

```html
xmlns:f="http://java.sun.com/jsf/core"
```

Let's switch from a raw EL expression to *outputText* which will allow us to
nest a child component to enhance its behavior. In this case, formatting the date.

```html
<h:outputText value="#{emp.dateOfBirth}">
    <f:convertDateTime pattern="MM/dd/yyyy"/>
</h:outputText>

<h:outputText value="#{emp.hireDate}">
    <f:convertDateTime pattern="MM/dd/yyyy"/>
</h:outputText>            
```

## Add some headers
Our table needs headers.  Again, we'll use a nested component called a *facet*.
A facet represents a named section on a containing component and is registered
with the component.  The advantage over a simple attribute like *headerText* on
the column is that a facet itself is a container so it can contain even more
components.  Consider a table with inline input filters as an example.

```html
<h:column>
    <f:facet name="header">Employee No</f:facet>
    #{emp.employeeNo}
</h:column>
<h:column>
    <f:facet name="header">First Name</f:facet>
    #{emp.firstName}
</h:column>
<h:column>
    <f:facet name="header">Last Name</f:facet>
    #{emp.lastName}
</h:column>
<h:column>
    <f:facet name="header">Date of Birth</f:facet>
    <h:outputText value="#{emp.dateOfBirth}">
        <f:convertDateTime pattern="MM/dd/yyyy"/>
    </h:outputText>
</h:column>
<h:column>
    <f:facet name="header">Hire Date</f:facet>
    <h:outputText value="#{emp.hireDate}">
        <f:convertDateTime pattern="MM/dd/yyyy"/>
    </h:outputText>
</h:column>        
```

## Let's give it some Style
Our table is ugly, let's pretty it up a bit.

```html
<link rel="stylesheet" type="text/css" href="application.css"/>
...
<h:dataTable value="#{employees}" var="emp" styleClass="employee-table">
```

application.css

```css
.employee-table {
    border-collapse: collapse;
}

.employee-table td, .employee-table th {
    padding: 4px;
    border: 1px solid #CCC;
}
```

# Form Submission
Let's add some interesting functionality.  We'll let the user search on employees
hired after a specified date.

Let's add a form to the top:

```html
<h:form>
    <h:panelGrid columns="3">
        <h:outputLabel for="hiredAfter" value="Hired After:"/>
        <h:inputText id="hiredAfter" value="#{employeeController.hiredAfter}"
                     converterMessage="Please enter a valid date. For example 4/15/2015.">
            <f:convertDateTime pattern="MM/dd/yyyy"/>
        </h:inputText>
        <h:message for="hiredAfter"/>
        <h:commandButton value="Submit"/>
    </h:panelGrid>
</h:form>
```

And in our controller:

```java
	private Date hiredAfter;

	@Produces
	@Named
	public List<Employee> getEmployees() {
		TypedQuery<Employee> query = entityManager
				.createQuery("select e from Employee e where e.hireDate >= :hiredAfter", Employee.class);
		query.setParameter("hiredAfter", hiredAfter);
		return query.getResultList();
	}

	public Date getHiredAfter() {
		return hiredAfter;
	}

	public void setHiredAfter(final Date hiredAfter) {
		this.hiredAfter = hiredAfter;
	}
```

# Navigation
Now that we have our results, let's let the user navigate to a page to edit an
Employee.

```html
	<h:link value="#{emp.employeeNo}" outcome="employee">
		<f:param name="empid" value="#{emp.employeeNo}"/>
	</h:link>
```

Note that the *outcome* is employee.  Absent navigation rules (which we'll get into later),
JSF will default to looking for an employee.xhml page.  So let's create it.

employee.xhtml

```html
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:f="http://java.sun.com/jsf/core">
<h:head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Employee</title>
    <link rel="stylesheet" type="text/css" href="application.css"/>
</h:head>
<h:body>
    <f:metadata>
        <f:viewParam name="empid" value="#{employeeController.employeeId}"/>
    </f:metadata>

    <p>
        You selected employee #{employeeController.employeeId}
    </p>
    <h:link outcome="index" value="Back"/>
</h:body>
</html>
```

This page requires a view parameter (URL parameter) with the name *empid*.  The view parameter
is binding it to the bean property *employeeId*, which we need to add:

```java
	private Integer employeeId;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(final Integer employeeId) {
		this.employeeId = employeeId;
	}
```

# Scoping
My page navigation works, but when I return my filter is gone!  Well, that's
because it's held in a request scoped bean.  Let's expand the scope.

```java
@SessionScoped
```

That's great, but the controller is now holding data for the entire user session.
Consider a case where you want to view scope some data and
session scope others.  For this reason, we create different classes
for different things: Controllers, Forms, data providers. Let's refactor.

```java
package com.example.ui.employee;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@SessionScoped
@Named
public class SearchForm implements Serializable {

	private Date hiredAfter;

	public Date getHiredAfter() {
		return hiredAfter;
	}

	public void setHiredAfter(final Date hiredAfter) {
		this.hiredAfter = hiredAfter;
	}
}
```

```java
package com.example.ui.employee;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class EmployeeForm {

	private Integer employeeId;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(final Integer employeeId) {
		this.employeeId = employeeId;
	}
}
```

```java
@RequestScoped
@Named
public class EmployeeController {

	@PersistenceContext
	EntityManager entityManager;

	@Inject
	SearchForm searchForm;

	@Produces
	@Named
	public List<Employee> getEmployees() {
		TypedQuery<Employee> query = entityManager
				.createQuery("select e from Employee e where e.hireDate >= :hiredAfter", Employee.class);
		query.setParameter("hiredAfter", searchForm.getHiredAfter());
		return query.getResultList();
	}
}
```

Much better ;)
Now let's fix the pages:

index.xhtml

```html
<h:inputText id="hiredAfter" value="#{searchForm.hiredAfter}"
             converterMessage="Please enter a valid date. For example 4/15/2015.">
```

employee.xhtml

```html
<f:metadata>
    <f:viewParam name="empid" value="#{employeeForm.employeeId}"/>
</f:metadata>

<p>
    You selected employee #{employeeForm.employeeId}
</p>
```

## Let's go further with a Repository
Why stop with refactoring the data.  The query may be used elsewhere and, for a
number of other reasons, we'll want to move our data operations to a DAO or Repository.

```java
package com.example.repository;

import com.example.Employee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Repository for the {@link Employee} entity.
 */
@Stateless
public class EmployeeRepository {

	@PersistenceContext
	EntityManager entityManager;

	public List<Employee> queryAll() {
		TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e", Employee.class);
		return query.getResultList();
	}

	public List<Employee> queryHiredAfter(Date hiredAfter) {
		TypedQuery<Employee> query = entityManager
				.createQuery("select e from Employee e where e.hireDate >= :hiredAfter", Employee.class);
		query.setParameter("hiredAfter", hiredAfter);
		return query.getResultList();
	}
}
```

So why is it *Stateless*?  Because in EE6, if you want to use JTA transactions (and you do), then
you need to use EJBs.  No worries, CDI and EJBs work well together.  In EE7, CDI will get transactions
and the you will likely kiss EJBs goodbye (unless you want to pool a resource).

Note that I added two queries: one to retrieve all employees and the other to retrieve with our filter.
Let's default to all when we don't have an input from the user for a better experience.

```java
	@Inject
	EmployeeRepository employeeRepository;

	@Inject
	SearchForm searchForm;

	@Produces
	@Named
	public List<Employee> getEmployees() {
		if (searchForm.getHiredAfter() == null) {
			return employeeRepository.queryAll();
		} else {
			return employeeRepository.queryHiredAfter(searchForm.getHiredAfter());
		}
	}
```

# Templates
So we refactored our Java code, let's clean up the XHTML.

template.xhtml

```html
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html">
<h:head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>#{pageTitle}</title>
    <link rel="stylesheet" type="text/css" href="application.css"/>
</h:head>
<h:body>
    Content is below this:
    <hr/>
    <ui:insert name="content"/>
</h:body>
</html>
```

index.xhtml

```xml
<ui:composition
        xmlns="http://www.w3.org/1999/xhtml"
        xmlns:f="http://java.sun.com/jsf/core"
        xmlns:ui="http://java.sun.com/jsf/facelets"
        xmlns:h="http://java.sun.com/jsf/html"
        template="template.xhtml">
    <ui:param name="pageTitle" value="Employees"/>
    <ui:define name="content">
    ...
    </ui:define>
</ui:composition>
```

# CRUD
Let's implement Edit and Delete.

## Edit

```java
@ViewScoped
@Named
public class EmployeeForm implements Serializable {

	private Integer employeeId;

	private Employee employee;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(final Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(final Employee employee) {
		this.employee = employee;
	}
}
```

EmployeeRepository.java

```java
	public Employee find(Integer id) {
		return entityManager.find(Employee.class, id);
	}

	public Employee merge(Employee employee) {
		return entityManager.merge(employee);
	}

	public void persist(Employee employee) {
		entityManager.persist(employee);
	}

	public void remove(Employee employee) {
		Employee attached = find(employee.getEmployeeNo());
		entityManager.remove(attached);
	}
```

EditEmployeeController.java

```java
package com.example.ui.employee;

import com.example.Employee;
import com.example.repository.EmployeeRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:mlassiter@lassitercg.com">Mark Lassiter</a>
 */
@RequestScoped
@Named
public class EditEmployeeController {

	@Inject
	EmployeeForm employeeForm;

	@Inject
	EmployeeRepository employeeRepository;

	public void save() {
		employeeRepository.merge(employeeForm.getEmployee());
	}

	public void preRenderViewEvent() {
		if (employeeForm.getEmployee() == null) {
			initializeEmployee();
		}
	}

	private void initializeEmployee() {
		if (employeeForm.getEmployeeId() == null) {
			employeeForm.setEmployee(new Employee());
			return;
		}
		Employee employee = employeeRepository.find(employeeForm.getEmployeeId());
		employeeForm.setEmployee(employee);
	}
}
```

employee.xhtml

```html
<ui:composition
        xmlns="http://www.w3.org/1999/xhtml"
        xmlns:f="http://java.sun.com/jsf/core"
        xmlns:ui="http://java.sun.com/jsf/facelets"
        xmlns:h="http://java.sun.com/jsf/html"
        template="template.xhtml">
    <ui:param name="pageTitle" value="Employee"/>
    <ui:define name="content">
        <f:metadata>
            <f:viewParam name="empid" value="#{employeeForm.employeeId}"/>
            <f:event type="preRenderView" listener="#{editEmployeeController.preRenderViewEvent}"/>
        </f:metadata>

        <h:form id="frmEmployee">
            <h:panelGrid columns="3">
                <h:outputLabel for="employeeId" value="Employee ID"/>
                <h:inputText id="employeeId" value="#{employeeForm.employee.employeeNo}" readonly="true"/>
                <h:message for="employeeId"/>

                <h:outputLabel for="firstName" value="First Name"/>
                <h:inputText id="firstName" value="#{employeeForm.employee.firstName}"/>
                <h:message for="firstName"/>

                <h:outputLabel for="lastName" value="Last Name"/>
                <h:inputText id="lastName" value="#{employeeForm.employee.lastName}"/>
                <h:message for="lastName"/>

                <h:outputLabel for="dateOfBirth" value="Date of Birth"/>
                <h:inputText id="dateOfBirth" value="#{employeeForm.employee.dateOfBirth}">
                    <f:convertDateTime pattern="MM/dd/yyyy"/>
                </h:inputText>
                <h:message for="dateOfBirth"/>

                <h:outputLabel for="hireDate" value="Hire Date"/>
                <h:inputText id="hireDate" value="#{employeeForm.employee.hireDate}">
                    <f:convertDateTime pattern="MM/dd/yyyy"/>
                </h:inputText>
                <h:message for="hireDate"/>

                <h:commandButton action="#{editEmployeeController.save}" value="Save"/>
                <h:link outcome="index" value="Cancel"/>
            </h:panelGrid>
        </h:form>
    </ui:define>
</ui:composition>
```

# Navigation Rules
It works great, but when I hit Save it stays on the same page?  Well we have yet
to tell JSF where to go after the save method is invoked.  Let's introduce a
navigation rule.  And while we are at it, instead of hard-coding the cancel
link to the index.xhtml page, let's add a rule for that too.

```xml
    <!-- Employee Edit Page -->
    <navigation-rule>
        <from-view-id>/employee.xhtml</from-view-id>
        <navigation-case>
            <from-action>#{editEmployeeController.save}</from-action>
            <if>#{not facesContext.isValidationFailed()}</if>
            <to-view-id>/index.xhtml</to-view-id>
            <redirect/>
        </navigation-case>
        <navigation-case>
            <from-outcome>cancel</from-outcome>
            <to-view-id>/index.xhtml</to-view-id>
            <redirect/>
        </navigation-case>
    </navigation-rule>
```

Much better :D

## New and Remove
Let's finish out our CRUD

index.xhtml

```html
<h:link value="New ..." outcome="employee"/>
...
	<h:column>
		<h:commandLink action="#{employeeController.remove(emp)}" value="Delete"/>
	</h:column>
```

EmployeeController.java

```java
	public void remove(Employee employee) {
		employeeRepository.remove(employee);
	}
```

## How about some feedback
I saw the row disappear but that's too subtle, so let's add a message.

index.xhtml

```html
<h:messages globalOnly="true" />
```

EmployeeController.java

```java
public void remove(Employee employee) {
  employeeRepository.remove(employee);
  FacesContext.getCurrentInstance()
		.addMessage(null, new FacesMessage("Successfully deleted employee " + employee.getEmployeeNo()));
}
```

## Ajax
Let's get out of 1990 and let's perform our search using AJAX.

```html
<f:ajax event="change" render=":frmEmployees @form"/>
...
<h:form id="frmEmployees">
```

## Summary
Clean lightweight HTML and no limitations.

# Primefaces!
Let's just do it:
```html
<ui:composition
        xmlns="http://www.w3.org/1999/xhtml"
        xmlns:f="http://java.sun.com/jsf/core"
        xmlns:ui="http://java.sun.com/jsf/facelets"
        xmlns:h="http://java.sun.com/jsf/html"
        xmlns:p="http://primefaces.org/ui"
        template="template.xhtml">
    <ui:param name="pageTitle" value="Example"/>
    <ui:define name="content">
        <p:growl globalOnly="true"/>
        <h:form>
            <h:panelGrid columns="3">
                <p:outputLabel for="hiredAfter" value="Hired After:"/>
                <p:calendar id="hiredAfter" value="#{searchForm.hiredAfter}"
                            pattern="MM/dd/yyyy" navigator="true"
                            converterMessage="Please enter a valid date. For example 4/15/2015.">
                    <f:convertDateTime pattern="MM/dd/yyyy"/>
                    <p:ajax event="dateSelect" update=":frmEmployees @form"/>
                </p:calendar>
                <p:message for="hiredAfter"/>
                <p:button value="New ..." outcome="employee"/>
            </h:panelGrid>
        </h:form>
        <h:form id="frmEmployees">
            <p:dataTable value="#{employees}" var="emp" styleClass="employee-table"
                         rows="10" paginator="true">
                <p:column sortBy="#{emp.employeeNo}">
                    <f:facet name="header">Employee No</f:facet>
                    <h:link value="#{emp.employeeNo}" outcome="employee">
                        <f:param name="empid" value="#{emp.employeeNo}"/>
                    </h:link>
                </p:column>
                <p:column sortBy="#{emp.firstName}">
                    <f:facet name="header">First Name</f:facet>
                    #{emp.firstName}
                </p:column>
                <p:column sortBy="#{emp.lastName}">
                    <f:facet name="header">Last Name</f:facet>
                    #{emp.lastName}
                </p:column>
                <p:column sortBy="#{emp.dateOfBirth}">
                    <f:facet name="header">Date of Birth</f:facet>
                    <h:outputText value="#{emp.dateOfBirth}">
                        <f:convertDateTime pattern="MM/dd/yyyy"/>
                    </h:outputText>
                </p:column>
                <p:column sortBy="#{emp.hireDate}">
                    <f:facet name="header">Hire Date</f:facet>
                    <h:outputText value="#{emp.hireDate}">
                        <f:convertDateTime pattern="MM/dd/yyyy"/>
                    </h:outputText>
                </p:column>
                <p:column width="80" style="text-align: center">
                    <p:commandButton action="#{employeeController.remove(emp)}" icon="ui-icon-trash"
                                     update="@form growl"
                                     style="width: 24px; height: 24px"/>
                </p:column>
            </p:dataTable>
        </h:form>
    </ui:define>
</ui:composition>
```

## Why doesn't my sort work?
The bean is request scoped, so each time there is a request, even an ajax
request for sorting, the data is queried again. Yikes!  Let's cache it
while the user is on the page.

EmployeeDatatable.java

```java
package com.example.ui.employee;

import com.example.Employee;
import com.example.repository.EmployeeRepository;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class EmployeeDatatable implements Serializable {

	@Inject
	EmployeeRepository employeeRepository;

	@Inject
	SearchForm searchForm;

	private List<Employee> values;

	public List<Employee> getValues() {
		if (values == null)
			refresh();
		return values;
	}

	public void refresh() {
		if (searchForm.getHiredAfter() == null) {
			values = employeeRepository.queryAll();
		} else {
			values = employeeRepository.queryHiredAfter(searchForm.getHiredAfter());
		}
	}
}
```

EmployeeController.java

```java
@RequestScoped
@Named
public class EmployeeController implements Serializable {

	@Inject
	EmployeeRepository employeeRepository;

	@Inject
	SearchForm searchForm;

	@Inject
	EmployeeDatatable employeeDatatable;

	public void remove(Employee employee) {
		employeeRepository.remove(employee);
		employeeDatatable.refresh();
		FacesContext.getCurrentInstance()
				.addMessage(null, new FacesMessage("Successfully deleted employee " + employee.getEmployeeNo()));
	}

}
```

index.xhtml

```html
<ui:composition
        xmlns="http://www.w3.org/1999/xhtml"
        xmlns:f="http://java.sun.com/jsf/core"
        xmlns:ui="http://java.sun.com/jsf/facelets"
        xmlns:h="http://java.sun.com/jsf/html"
        xmlns:p="http://primefaces.org/ui"
        template="template.xhtml">
    <ui:param name="pageTitle" value="Example"/>
    <ui:define name="content">
        <p:growl globalOnly="true"/>
        <h:form>
            <h:panelGrid columns="3">
                <p:outputLabel for="hiredAfter" value="Hired After:"/>
                <p:calendar id="hiredAfter" value="#{searchForm.hiredAfter}"
                            pattern="MM/dd/yyyy" navigator="true"
                            converterMessage="Please enter a valid date. For example 4/15/2015.">
                    <f:convertDateTime pattern="MM/dd/yyyy"/>
                    <p:ajax event="dateSelect" listener="#{employeeDatatable.refresh}" update=":frmEmployees @form"/>
                </p:calendar>
                <p:message for="hiredAfter"/>
                <p:button value="New ..." outcome="employee"/>
            </h:panelGrid>
        </h:form>
        <h:form id="frmEmployees">
            <p:dataTable value="#{employeeDatatable.values}" var="emp" styleClass="employee-table"
                         rows="10" paginator="true">
                <p:column sortBy="#{emp.employeeNo}">
                    <f:facet name="header">Employee No</f:facet>
                    <h:link value="#{emp.employeeNo}" outcome="employee">
                        <f:param name="empid" value="#{emp.employeeNo}"/>
                    </h:link>
                </p:column>
                <p:column sortBy="#{emp.firstName}">
                    <f:facet name="header">First Name</f:facet>
                    #{emp.firstName}
                </p:column>
                <p:column sortBy="#{emp.lastName}">
                    <f:facet name="header">Last Name</f:facet>
                    #{emp.lastName}
                </p:column>
                <p:column sortBy="#{emp.dateOfBirth}">
                    <f:facet name="header">Date of Birth</f:facet>
                    <h:outputText value="#{emp.dateOfBirth}">
                        <f:convertDateTime pattern="MM/dd/yyyy"/>
                    </h:outputText>
                </p:column>
                <p:column sortBy="#{emp.hireDate}">
                    <f:facet name="header">Hire Date</f:facet>
                    <h:outputText value="#{emp.hireDate}">
                        <f:convertDateTime pattern="MM/dd/yyyy"/>
                    </h:outputText>
                </p:column>
                <p:column width="80" style="text-align: center">
                    <p:commandButton action="#{employeeController.remove(emp)}" icon="ui-icon-trash"
                                     update="@form growl"
                                     style="width: 24px; height: 24px"/>
                </p:column>
            </p:dataTable>
        </h:form>
    </ui:define>
</ui:composition>
```
