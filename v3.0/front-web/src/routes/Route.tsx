import React, { useContext } from 'react';
import { Route as ReactDomRoute, RouteProps as ReactRouterProps, Redirect } from 'react-router-dom'
import {AuthContext} from "../contexts/AuthContext";
import Navbar from "../components/Navbar";

interface RouteProps extends ReactRouterProps {
  isPrivate?: boolean;
  hasNavBar?: boolean;
  component: React.ComponentType<ReactRouterProps>
}
const Route: React.FC<RouteProps> = ({ isPrivate = false, component: Component, hasNavBar, ...rest }) => {
  const { user } = useContext(AuthContext);
  return (
    <ReactDomRoute {...rest} render={(props) => {
      return isPrivate === !!user ? (
        <div>
          {hasNavBar && <Navbar /> }
          <Component {...props} />
        </div>
      ) : (
        <Redirect to={{ pathname: isPrivate ? '/' : '/categories' }} />
      )
    }} />
  )
};

export default Route;