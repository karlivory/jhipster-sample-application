import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/device">
      <Translate contentKey="global.menu.entities.device" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/controller-device">
      <Translate contentKey="global.menu.entities.controllerDevice" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/actuator-device">
      <Translate contentKey="global.menu.entities.actuatorDevice" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sensor-device">
      <Translate contentKey="global.menu.entities.sensorDevice" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/reading">
      <Translate contentKey="global.menu.entities.reading" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/current-reading">
      <Translate contentKey="global.menu.entities.currentReading" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
