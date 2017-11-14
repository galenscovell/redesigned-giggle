package galenscovell.sandbox.stateMachines;

import galenscovell.sandbox.ecs.component.StateComponent;


public enum PlayerAgent implements State<StateComponent> {
    DEFAULT() {
        @Override
        public void enter(StateComponent stateComponent) {
            System.out.println("Player Enter DEFAULT");
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println("Player Exit DEFAULT");
        }

        @Override
        public void update(StateComponent stateComponent) {

        }

        @Override
        public float getFrameRatio() {
            return 0;
        }

        @Override
        public String getName() {
            return "default";
        }
    }
}
