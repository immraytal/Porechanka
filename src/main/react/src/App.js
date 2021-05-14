import { createBrowserHistory } from "history";
import { useEffect } from "react";
import { Route, Switch, useHistory } from "react-router";
import { BrowserRouter } from "react-router-dom";
import "./App.css";
import AdvertInfo from "./classes/AdvertInfo";
import AdvertsListByCategory from "./classes/AdvertListByCategory";
import AdvertsList from "./classes/AdvertsList";
import LoginPage from "./classes/LoginPage";
import ProfilePage from "./classes/ProfilePage";
import ProfileStats from "./classes/ProfileStats";
import Header from "./Header";

function App() {
  const history = createBrowserHistory();
  return (
    <BrowserRouter history={history}>
      <Header />
      <Switch>
        <Route exact path="/" component={AdvertsList} />
        <Route exact path="/login" component={LoginPage} />
        <Route exact path="/profile" component={ProfilePage} />
        <Route exact path="/profile/stats" component={ProfileStats} />
        <Route exact path="/adverts?category=:categoryId" component={AdvertsListByCategory} />
        <Route exact path="/adverts" component={AdvertsList} />
        <Route path="/adverts/:advertId" component={AdvertInfo} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;
