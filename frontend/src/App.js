import './App.css';
import ReduxThunk from 'redux-thunk';
import AOS from 'aos'
import 'aos/dist/aos.css';
import {ConnectedRouter, connectRouter, routerMiddleware} from "connected-react-router";
import {createBrowserHistory} from "history";
import {applyMiddleware, combineReducers, createStore} from "redux";
import {Provider} from "react-redux";
import {BrowserRouter} from "react-router-dom";
import Router from "./Router/Router";
import {useEffect} from "react";

export const history = createBrowserHistory({basename: '/'});

const reducer = (history) => combineReducers({
    router: connectRouter(history),
});

const store = createStore(reducer(history), applyMiddleware(ReduxThunk, routerMiddleware(history)));


function App() {

    useEffect(() => {
        AOS.init();
        AOS.refresh();
    }, []);

  return (

      <Provider store={store}>
          <ConnectedRouter history={history}>
              <BrowserRouter>
                  <Router/>
              </BrowserRouter>
          </ConnectedRouter>
      </Provider>

  )

}

export default App;
