import React, { useState, useEffect } from 'react';
import { JobTable } from '../../components/elements/table';
import { getJobs } from '../../services/jobs';
import { Job } from '../../components/interfaces/Employee';

const JobsList: React.FC = () => {
       const [jobs, setJobs] = useState<Job[]>([]);
   
       const fetchJobs = async () => {
           try {
                const data = await getJobs();
                setJobs(data);
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