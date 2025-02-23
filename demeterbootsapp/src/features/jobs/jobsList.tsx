import React, { useState, useEffect } from 'react';
import { JobTable } from '../../components/elements/table';

const JobsList: React.FC = () => {
   
       const dummyJobsData = [
           { 'Task ID': 'J00000001', 'Employee Name': 'John Doe', Status: 'Preprocessing' },
           { 'Task ID': 'J00000002', 'Employee Name': 'Jane Doe', Status: 'Finished' }
         ];
   
       const [jobs, setJobs] = useState(dummyJobsData);
   
       const fetchJobs = async () => {
           try {
               setJobs(dummyJobsData);
           } catch (error) {
               console.error("Error fetching jobs:", error);
           }
       };
   
       useEffect(() => {
           fetchJobs();
       }, []);
         
       return (
           <div className="job-list">
               <JobTable data={jobs}/>
           </div>
       )
}

export default JobsList;