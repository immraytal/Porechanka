import { Route, Switch } from "react-router";
import { BrowserRouter } from "react-router-dom";
import "./App.css";
import AdvertInfo from "./classes/AdvertInfo";
import AdvertsList from "./classes/AdvertsList";
import LoginPage from "./classes/LoginPage";

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={AdvertsList} />
        <Route exact path="/login" component={LoginPage} />
        <Route exact path="/adverts" component={AdvertsList} />
        <Route path="/adverts/:advertId" component={AdvertInfo} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;
