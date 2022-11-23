-- Subqueries
-- Can reference the outer queries context (and named objects!)
SELECT Name as Employee FROM Employee e
WHERE Salary > (
    Select Salary FROM Employee m WHERE m.Id = e.ManagerId
)

-- Could also just make 2 references to the Table
select E1.Name 
from Employee as E1, Employee as E2 
where E1.ManagerId = E2.Id and E1.Salary > E2.Salary