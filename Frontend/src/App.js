import React, {useEffect, useState} from 'react'
import { Home } from "./components/home";
import LoginComponent from "./components/LoginComponent";


function App() {

  const [openDemoCard, setOpenDemoCard] = useState(true);

  const onOpenDemoCard = () => {
    setOpenDemoCard(false);
  }

  useEffect(() => {
    if (localStorage.getItem('notNew')!=null) {
      setOpenDemoCard(false);
    }
  },[])


  if(localStorage.getItem('user')==null){
    return (
      <div className="App">
        <LoginComponent openDemoCard = {openDemoCard} onOpenDemoCard = {onOpenDemoCard}/>
        </div>
    )
}
return (
      <div className="App">
        <Home/>
      </div>
    );
}

export default App;
