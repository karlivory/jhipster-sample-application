import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SensorDevice from './sensor-device';
import SensorDeviceDetail from './sensor-device-detail';
import SensorDeviceUpdate from './sensor-device-update';
import SensorDeviceDeleteDialog from './sensor-device-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SensorDeviceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SensorDeviceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SensorDeviceDetail} />
      <ErrorBoundaryRoute path={match.url} component={SensorDevice} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SensorDeviceDeleteDialog} />
  </>
);

export default Routes;
