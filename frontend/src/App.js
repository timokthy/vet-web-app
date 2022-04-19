import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from "./pages/app-portal/Login";
import Home from "./pages/app-portal/Home";

import AddAnimal from "./pages/app-portal/AddAnimal";
import ManageAnimals from "./pages/app-portal/ManageAnimals";

import AddUser from "./pages/user/AddUser";
import EditUser from "./pages/user/EditUser";
import BlockUser from "./pages/user/BlockUser";

import AnimalInfo from "./pages/animal/AnimalInfo";
import HealthRecords from './pages/animal/HealthRecords';
import Reminders from './pages/animal/Reminders';
import AddReminder from './pages/animal/AddReminder';
import Comments from './pages/animal/Comments';
import SingleComment from './pages/animal/SingleComment';
import Photos from './pages/animal/Photos';
import WeightHistory from './pages/animal/WeightHistory';
import SendAlert from './pages/animal/SendAlert';

import RequestTreatment from './pages/animal/RequestTreatment';
import RequestAnimal from "./pages/animal/RequestAnimal";
import UpdateTreatment from './pages/treatment/UpdateTreatment';
import AddDiagnosis from './pages/treatment/AddDiagnosis';
import UpdateDiagnosis from './pages/treatment/UpdateDiagnosis';
import AddTreatment from './pages/treatment/AddTreatment';



import ViewRequests from './pages/animal/ViewRequests';

function App() {
  return (
        <Router>
            <Routes>
                <Route path='/' element={<Login/>}/>
                <Route path='/home' element={<Home />}/>

                <Route path='/add-animal' element={<AddAnimal/>}/>
                <Route path='/add-treatment' element={<AddTreatment/>}/> 
                <Route path='/manage-animals' element={<ManageAnimals/>}/>

                <Route path='/add-user' element={<AddUser/>}/>
                <Route path='/edit-user' element={<EditUser/>}/>
                <Route path='/block-user' element={<BlockUser/>}/>

                <Route path='/animal-info' element={<AnimalInfo/>}/>
                <Route path='/health-records' element={<HealthRecords/>}/>
                <Route path='/reminders' element={<Reminders/>}/> 
                <Route path='/reminders/add' element={<AddReminder/>}/> 
                <Route path='/comments' element={<Comments/>}/> 
                <Route path='/comments/single' element={<SingleComment/>}/> 
                <Route path='/photos' element={<Photos/>}/>
                <Route path='/weight-history' element={<WeightHistory/>}/> 
                <Route path='/send-alert' element={<SendAlert/>}/> 
                {/* Send Alert */}

                <Route path='/request-treatment' element={<RequestTreatment/>}/> 
                <Route path='/request-animal' element={<RequestAnimal/>}/>
                <Route path='/view-requests' element={<ViewRequests/>}/>
                <Route path='/add-treatment' element={<AddTreatment/>}/> 
                <Route path='/update-treatment/single' element={<UpdateTreatment/>}/> 
                <Route path='/add-diagnosis' element={<AddDiagnosis/>}/> 
                <Route path='/update-diagnosis/single' element={<UpdateDiagnosis/>}/> 
            </Routes>
        </Router>
    );
}

export default App;
