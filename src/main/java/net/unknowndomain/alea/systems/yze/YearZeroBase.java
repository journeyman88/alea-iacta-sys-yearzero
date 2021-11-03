/*
 * Copyright 2021 m.bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems.yze;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.unknowndomain.alea.random.SingleResult;

/**
 *
 * @author m.bignami
 */
public abstract class YearZeroBase
{
    protected final Set<YearZeroModifiers> mods;

    public YearZeroBase(Collection<YearZeroModifiers> mod)
    {
        this.mods = new HashSet<>();
        this.mods.addAll(mod);
    }
    
    protected YearZeroResults buildResults(List<SingleResult<Integer>> baseResult, List<SingleResult<Integer>> gearResult, List<SingleResult<Integer>> skillResult, List<SingleResult<Integer>> stressResult)
    {
        return null;
    }
}
