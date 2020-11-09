import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ControllerDevice from './controller-device';
import ControllerDeviceDetail from './controller-device-detail';
import ControllerDeviceUpdate from './controller-device-update';
import ControllerDeviceDeleteDialog from './controller-device-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ControllerDeviceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ControllerDeviceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ControllerDeviceDetail} />
      <ErrorBoundaryRoute path={match.url} component={ControllerDevice} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ControllerDeviceDeleteDialog} />
  </>
);

export default Routes;
