import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDevice } from 'app/shared/model/device.model';
import { getEntities as getDevices } from 'app/entities/device/device.reducer';
import { getEntity, updateEntity, createEntity, reset } from './reading.reducer';
import { IReading } from 'app/shared/model/reading.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IReadingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ReadingUpdate = (props: IReadingUpdateProps) => {
  const [deviceId, setDeviceId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { readingEntity, devices, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/reading');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDevices();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.timestamp = convertDateTimeToServer(values.timestamp);

    if (errors.length === 0) {
      const entity = {
        ...readingEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.reading.home.createOrEditLabel">
            <Translate contentKey="jhipsterSampleApplicationApp.reading.home.createOrEditLabel">Create or edit a Reading</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : readingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="reading-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="reading-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="reading-type">
                  <Translate contentKey="jhipsterSampleApplicationApp.reading.type">Type</Translate>
                </Label>
                <AvInput
                  id="reading-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && readingEntity.type) || 'TEMPERATURE'}
                >
                  <option value="TEMPERATURE">{translate('jhipsterSampleApplicationApp.ReadingType.TEMPERATURE')}</option>
                  <option value="CO2">{translate('jhipsterSampleApplicationApp.ReadingType.CO2')}</option>
                  <option value="HUMIDITY">{translate('jhipsterSampleApplicationApp.ReadingType.HUMIDITY')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="valueLabel" for="reading-value">
                  <Translate contentKey="jhipsterSampleApplicationApp.reading.value">Value</Translate>
                </Label>
                <AvField id="reading-value" type="string" className="form-control" name="value" />
              </AvGroup>
              <AvGroup>
                <Label id="timestampLabel" for="reading-timestamp">
                  <Translate contentKey="jhipsterSampleApplicationApp.reading.timestamp">Timestamp</Translate>
                </Label>
                <AvInput
                  id="reading-timestamp"
                  type="datetime-local"
                  className="form-control"
                  name="timestamp"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.readingEntity.timestamp)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="reading-device">
                  <Translate contentKey="jhipsterSampleApplicationApp.reading.device">Device</Translate>
                </Label>
                <AvInput id="reading-device" type="select" className="form-control" name="device.id">
                  <option value="" key="0" />
                  {devices
                    ? devices.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/reading" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  devices: storeState.device.entities,
  readingEntity: storeState.reading.entity,
  loading: storeState.reading.loading,
  updating: storeState.reading.updating,
  updateSuccess: storeState.reading.updateSuccess,
});

const mapDispatchToProps = {
  getDevices,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ReadingUpdate);
