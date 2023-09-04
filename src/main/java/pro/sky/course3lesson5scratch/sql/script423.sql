select *
from student as s
         right join public.faculty f on f.id = s.faculty_id;

select a.student_id, s.name
from avatar as a
         inner join public.student s on s.id = a.student_id
order by a.student_id;