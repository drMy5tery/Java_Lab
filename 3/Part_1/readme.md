# Payroll System

A simple Java payroll system with an `Employee` base class and three derived classes: `HourlyEmployee`, `SalariedEmployee`, and `ExecutiveEmployee`. Each class calculates bonuses and weekly salaries based on the employee type.

## Class Overview

- **Employee**: Base class with common attributes (`employeeId`, `employeeName`, `designation`) and methods (`displayInfo`, `calculateBonus`).
- **HourlyEmployee**: Calculates weekly salary based on hourly rate and hours worked, with a 10% weekly bonus.
- **SalariedEmployee**: Calculates weekly salary from a monthly salary with a 20% weekly bonus.
- **ExecutiveEmployee**: Inherits from `SalariedEmployee` and adds an annual bonus based on a specified percentage.

## Usage

The main program (`Part_1`) creates different employee objects, displays their details, and calculates the total payroll.

