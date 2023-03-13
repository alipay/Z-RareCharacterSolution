import { animated, useSpring } from '@react-spring/web';
import classnames from 'classnames';
import React, { ReactElement, useState } from 'react';
import Mask from './Mask';
import { ShouldRender } from './should-render';
import useUnmountedRef from './use-unmount-ref';
import './index.less';

interface IProps {
  className?: string;
  maskClassName?: string;
  position: 'bottom' | 'top' | 'left' | 'right';
  visible: boolean;
  mask?: boolean;
  forceRender?: boolean;
  destroyOnClose?: boolean;
  closeOnMaskClick?: boolean;
  onMaskClick?: (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
  onClose?: () => void;
  afterClose?: () => void;
  afterShow?: () => void;
  children: ReactElement;
}

const overflowValue = document.body.style.overflow;

const Popup: React.FC<IProps> = (props) => {
  const [active, setActive] = useState(props.visible);
  const [maskVisible, setMaskVisiable] = useState(active && props.visible);
  const newClassName = classnames('rare-words-input__popup', props.className);
  const maskClassName = classnames(
    'rare-words-input__popup-mask',
    props.maskClassName,
  );
  const contentClassName = classnames(
    'rare-words-input__popup-content',
    `rare-words-input__popup-position-${props.position}`,
  );

  const handleMaskClick = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (props.onMaskClick) props.onMaskClick?.(e);
    if (props.closeOnMaskClick) props.onClose?.();
  };

  const unmountedRef = useUnmountedRef();
  const { percent } = useSpring({
    percent: props.visible ? 0 : 100,
    config: {
      precision: 0.1,
      mass: 0.4,
      tension: 300,
      friction: 30,
    },
    onRest: () => {
      if (unmountedRef.current) return;
      setActive(props.visible);
      if (props.visible) {
        props.afterShow?.();
      } else {
        props.afterClose?.();
      }
    },
  });

  React.useEffect(() => {
    if (props.visible) {
      setActive(true);
    }
  }, [props.visible]);

  React.useEffect(() => {
    setMaskVisiable(active && props.visible);
  }, [active, props.visible]);

  // 防止滚动
  React.useEffect(() => {
    if (active) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = overflowValue;
    }
  }, [active]);

  return (
    <ShouldRender
      active={active}
      forceRender={props.forceRender}
      destroyOnClose={props.destroyOnClose}
    >
      <div
        className={newClassName}
        style={{ display: active ? undefined : 'none' }}
      >
        {props.mask && (
          <Mask
            className={maskClassName}
            visible={maskVisible}
            forceRender={props.forceRender}
            destroyOnClose={props.destroyOnClose}
            onMaskClick={handleMaskClick}
          />
        )}
        <animated.div
          className={contentClassName}
          style={{
            transform: percent.to((v) => {
              if (props.position === 'bottom') {
                return `translate(0, ${v}%)`;
              }
              if (props.position === 'top') {
                return `translate(0, -${v}%)`;
              }
              if (props.position === 'left') {
                return `translate(-${v}%, 0)`;
              }
              if (props.position === 'right') {
                return `translate(${v}%, 0)`;
              }
              return 'none';
            }),
          }}
        >
          {props.children}
        </animated.div>
      </div>
    </ShouldRender>
  );
};

export { Popup };
