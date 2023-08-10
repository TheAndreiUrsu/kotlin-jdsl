package com.linecorp.kotlinjdsl.benchmark.entity.employee

import jakarta.persistence.*

@DiscriminatorValue("FULL_TIME")
@Entity
class FullTimeEmployee(
    override val employeeId: Long,

    override var name: String,

    override var nickname: String?,

    override var phone: String,

    override var address: EmployeeAddress,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "annual_salary"))
    var annualSalary: EmployeeSalary,

    @OneToMany(mappedBy = "employee")
    override val departments: MutableSet<EmployeeDepartment>,
) : Employee(
    employeeId = employeeId,
    name = name,
    nickname = nickname,
    phone = phone,
    address = address,
    departments = departments,
)
