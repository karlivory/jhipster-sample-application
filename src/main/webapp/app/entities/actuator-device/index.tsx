import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ActuatorDevice from './actuator-device';
import ActuatorDeviceDetail from './actuator-device-detail';
import ActuatorDeviceUpdate from './actuator-device-update';
import ActuatorDeviceDeleteDialog from './actuator-device-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ActuatorDeviceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ActuatorDeviceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ActuatorDeviceDetail} />
      <ErrorBoundaryRoute path={match.url} component={ActuatorDevice} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ActuatorDeviceDeleteDialog} />
  </>
);

export default Routes;
