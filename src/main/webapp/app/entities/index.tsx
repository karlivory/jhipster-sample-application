import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Device from './device';
import ControllerDevice from './controller-device';
import ActuatorDevice from './actuator-device';
import SensorDevice from './sensor-device';
import Reading from './reading';
import CurrentReading from './current-reading';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}device`} component={Device} />
      <ErrorBoundaryRoute path={`${match.url}controller-device`} component={ControllerDevice} />
      <ErrorBoundaryRoute path={`${match.url}actuator-device`} component={ActuatorDevice} />
      <ErrorBoundaryRoute path={`${match.url}sensor-device`} component={SensorDevice} />
      <ErrorBoundaryRoute path={`${match.url}reading`} component={Reading} />
      <ErrorBoundaryRoute path={`${match.url}current-reading`} component={CurrentReading} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
