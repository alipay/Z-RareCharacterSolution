import classnames from 'classnames';
import React, { useState } from 'react';
import { ICommonError } from '../../typings';
import './index.less';

const ErrorPage: React.FC<ICommonError> = (props) => {
  const [loading, setLoading] = useState(false);
  const handleRetry = () => {
    setLoading(true);
    if (props.onRetry) {
      props.onRetry().catch(() => {
        setLoading(false);
      });
    }
  };

  return (
    <div className="rare-words-input__error">
      <img
        className="rare-words-input__error-img"
        src="https://gw.alipayobjects.com/mdn/rms_997765/afts/img/A*i4GUT4z04egAAAAAAAAAAAAAARQnAQ"
        alt="错误"
      />
      <p className="rare-words-input__error-desc">
        {props.message || '人气大爆发'}
      </p>
      <button
        type="button"
        className={classnames(
          'rare-words-input__error-btn',
          loading ? 'loading' : '',
        )}
        onClick={handleRetry}
      >
        重试
      </button>
    </div>
  );
};

export { ErrorPage };
