import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './reading.reducer';
import { IReading } from 'app/shared/model/reading.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IReadingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ReadingDetail = (props: IReadingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { readingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jhipsterSampleApplicationApp.reading.detail.title">Reading</Translate> [<b>{readingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="type">
              <Translate contentKey="jhipsterSampleApplicationApp.reading.type">Type</Translate>
            </span>
          </dt>
          <dd>{readingEntity.type}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="jhipsterSampleApplicationApp.reading.value">Value</Translate>
            </span>
          </dt>
          <dd>{readingEntity.value}</dd>
          <dt>
            <span id="timestamp">
              <Translate contentKey="jhipsterSampleApplicationApp.reading.timestamp">Timestamp</Translate>
            </span>
          </dt>
          <dd>{readingEntity.timestamp ? <TextFormat value={readingEntity.timestamp} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.reading.device">Device</Translate>
          </dt>
          <dd>{readingEntity.device ? readingEntity.device.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/reading" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reading/${readingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ reading }: IRootState) => ({
  readingEntity: reading.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ReadingDetail);
