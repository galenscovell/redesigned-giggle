package galenscovell.sandbox.stateMachines;

import galenscovell.sandbox.ecs.component.StateComponent;

public enum CropAgent implements State<StateComponent> {
    DEFAULT() {
        @Override
        public void enter(StateComponent stateComponent) {
            System.out.println("Crop Enter DEFAULT");
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println("Crop Exit DEFAULT");
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
